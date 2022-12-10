package perobobbot.bus.api;

import lombok.NonNull;

public record RegularTopic(@NonNull String topicAsString, @NonNull String namespace, @NonNull String tenant,
                           @NonNull String name) implements Topic {
    @Override
    public boolean matches(@NonNull String topicAsString) {
        return topicAsString.equals(topicAsString);
    }

    public @NonNull String getNamespaceAndTenant() {
        return namespace + ":" + tenant;
    }
}
