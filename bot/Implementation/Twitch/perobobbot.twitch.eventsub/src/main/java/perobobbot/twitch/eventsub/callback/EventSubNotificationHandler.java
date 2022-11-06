package perobobbot.twitch.eventsub.callback;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.bus.Bus;
import perobobbot.tools.MessageSaver;
import perobobbot.twitch.api.eventsub.EventSubNotification;
import perobobbot.twitch.api.eventsub.EventSubRequest;
import perobobbot.twitch.api.eventsub.EventSubVerification;
import perobobbot.twitch.api.eventsub.TwitchEventSubConfiguration;
import perobobbot.twitch.eventsub.TwitchRequestContent;
import perobobbot.twitch.eventsub.TwitchRequestDeserializer;
import perobobbot.twitch.eventsub.TwitchRequestValidator;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class EventSubNotificationHandler implements EventSubHandler {

    private final @NonNull ObjectMapper objectMapper;
    private final @NonNull TwitchEventSubConfiguration configuration;
    private final @NonNull Bus bus;
    private final @NonNull MessageSaver messageSaver = new MessageSaver("event_sub", ".json");

    @Override
    public int priority() {
        return 200;
    }

    @Override
    public @NonNull HttpResponse<?> handleCall(@NonNull HttpRequest<?> request, @NonNull String body, @NonNull CallContext context) {
        return new Executor(request,body).execute();
    }

    @RequiredArgsConstructor
    private class Executor {
        private final @NonNull HttpRequest<?> request;
        private final @NonNull String body;
        private TwitchRequestContent<String> validatedRequest;
        private EventSubRequest eventSubRequest;
        private HttpResponse<?> response;

        public @NonNull HttpResponse<?> execute() {
            LOG.debug("[EventSub] Receive request");
            this.validateRequest();
            if (requestIsNotValid()) {
                LOG.debug("[EventSub] request is invalid");
                return HttpResponse.notFound();
            }
            this.saveBodyContent();
            this.deserializeRequestBody();
            LOG.debug("[EventSub] deserialized : " + eventSubRequest);
            this.dispatchEventSubRequest();
            this.prepareResponse();
            return response;
        }


        private void validateRequest() {
            validatedRequest = TwitchRequestValidator.validate(request, body,configuration.getSecret()).orElse(null);
        }

        private boolean requestIsNotValid() {
            return validatedRequest == null;
        }

        private void saveBodyContent() {
            if (configuration.isBackupMessages()) {
                LOG.debug("[EventSub] Save body message");
                messageSaver.saveMessage(validatedRequest.content());
            }
        }

        private void deserializeRequestBody() {
            eventSubRequest = TwitchRequestDeserializer.deserialize(objectMapper, validatedRequest.type(), validatedRequest.content()).orElse(null);
        }


        private void dispatchEventSubRequest() {
            if (eventSubRequest instanceof EventSubNotification notification) {
                notification.getEvents().forEach(bus::publishEvent);
            }
        }

        private void prepareResponse() {
            if (eventSubRequest instanceof EventSubVerification verification) {
                LOG.info("Send challenge : {}", verification.getChallenge());
                this.response = HttpResponse.ok(verification.getChallenge());
            } else {
                this.response = HttpResponse.ok();
            }
        }

    }


}
