package perobobbot.bus.api;

public record RegularTopic(String topicAsString, String namespace, String tenant,
                           String name) implements Topic {
    @Override
    public boolean matches(String topicAsString) {
        return this.topicAsString.equals(topicAsString);
    }

    public String getNamespaceAndTenant() {
        return namespace + ":" + tenant;
    }
}
