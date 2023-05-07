package perobobbot.twitch.eventsub.callback;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.serde.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.SubscriptionManager;
import perobobbot.bus.api.Bus;
import perobobbot.bus.api.Producer;
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

    private final ObjectMapper objectMapper;
    private final TwitchEventSubConfiguration configuration;
    private final Bus bus;
    private final MessageSaver messageSaver = new MessageSaver("event_sub", ".json");

    @Override
    public int priority() {
        return 200;
    }

    @Override
    public HttpResponse<?> handleCall(HttpRequest<?> request, String body, CallContext context) {
        return new Executor(request, body).execute();
    }


    @RequiredArgsConstructor
    private class Executor {
        private final HttpRequest<?> request;
        private final String body;
        private @Nullable TwitchRequestContent<String> validatedRequest;
        private @Nullable EventSubRequest eventSubRequest;

        public HttpResponse<?> execute() {
            try (var producer = bus.createProducer(SubscriptionManager.SUBSCRIPTION_EVENT_TOPIC)) {
                return execute(producer);
            }
        }

        private HttpResponse<?> execute(Producer producer) {
            LOG.debug("[EventSub] Receive request");
            this.validateRequest();
            if (requestIsNotValid()) {
                LOG.debug("[EventSub] request is invalid");
                return HttpResponse.notFound();
            }
            this.saveBodyContent();
            this.deserializeRequestBody();
            LOG.debug("[EventSub] deserialized : " + eventSubRequest);
            this.dispatchEventSubRequest(producer);
            return this.prepareResponse();
        }


        private void validateRequest() {
            validatedRequest = TwitchRequestValidator.validate(request, body, configuration.getSecret()).orElse(null);
        }

        private boolean requestIsNotValid() {
            return validatedRequest == null;
        }

        private void saveBodyContent() {
            assert validatedRequest != null;
            if (configuration.isBackupMessages()) {
                LOG.debug("[EventSub] Save body message");
                messageSaver.saveMessage(validatedRequest.content());
            }
        }

        private void deserializeRequestBody() {
            assert validatedRequest != null;
            eventSubRequest = TwitchRequestDeserializer.deserialize(objectMapper, validatedRequest.type(), validatedRequest.content()).orElse(null);
        }


        private void dispatchEventSubRequest(Producer producer) {
            if (eventSubRequest instanceof EventSubNotification notification) {
                notification.getEvents().forEach(producer::send);
            }
        }

        private HttpResponse<?> prepareResponse() {
            if (eventSubRequest instanceof EventSubVerification verification) {
                LOG.info("Send challenge : {}", verification.getChallenge());
                return HttpResponse.ok(verification.getChallenge());
            } else {
                return HttpResponse.ok();
            }
        }

    }


}
