package perobobbot.twitch.eventsub.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.PerobobbotExecutors;
import perobobbot.tools.MessageSaver;
import perobobbot.twitch.api.eventsub.EventSubRequest;
import perobobbot.twitch.api.eventsub.EventSubRequestDispatcher;
import perobobbot.twitch.api.eventsub.EventSubVerification;
import perobobbot.twitch.api.eventsub.TwitchEventSubConfiguration;
import perobobbot.twitch.eventsub.TwitchRequestContent;
import perobobbot.twitch.eventsub.TwitchRequestDeserializer;
import perobobbot.twitch.eventsub.TwitchRequestValidator;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class EventSubNotificationHandler implements EventSubHandler {

    private final @NonNull PerobobbotExecutors executors;
    private final @NonNull ObjectMapper objectMapper;
    private final @NonNull TwitchEventSubConfiguration configuration;
    private final @NonNull EventSubRequestDispatcher eventDispatcher;
    private final @NonNull MessageSaver messageSaver = new MessageSaver("event_sub", ".json");

    @Override
    public int priority() {
        return 200;
    }

    @Override
    public @NonNull HttpResponse<?> handleCall(@NonNull HttpRequest<?> request, @NonNull CallContext context) {
        return new Executor(request).execute();
    }

    @RequiredArgsConstructor
    private class Executor {
        private final @NonNull HttpRequest<?> request;
        private TwitchRequestContent<String> validatedRequest;
        private EventSubRequest eventSubRequest;
        private HttpResponse<?> response;

        public @NonNull HttpResponse<?> execute() {
            this.validateRequest();
            if (requestIsNotValid()) {
                return HttpResponse.notFound();
            }
            this.saveBodyContent();
            this.deserializeRequestBody();
            this.dispatchNotification();
            this.prepareResponse();
            return response;
        }


        private void validateRequest() {
            validatedRequest = TwitchRequestValidator.validate(request, configuration.getSecret()).orElse(null);
        }

        private boolean requestIsNotValid() {
            return validatedRequest == null;
        }

        private void saveBodyContent() {
            if (configuration.isBackupMessages()) {
                messageSaver.saveMessage(validatedRequest.content());
            }
        }

        private void deserializeRequestBody() {
            eventSubRequest = TwitchRequestDeserializer.deserialize(objectMapper, validatedRequest.type(), validatedRequest.content()).orElse(null);
        }


        private void dispatchNotification() {
            if (eventSubRequest != null) {
                executors.submit(() -> eventDispatcher.dispatchEventSub(eventSubRequest));
            }
        }

        private void prepareResponse() {
            if (eventSubRequest instanceof EventSubVerification verification) {
                LOG.info("Send challenge : {}",verification.getChallenge());
                this.response = HttpResponse.ok(verification.getChallenge());
            } else {
                this.response = HttpResponse.ok();
            }
        }

    }


}
