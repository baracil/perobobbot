package perobobbot.bom.generator;

import lombok.NonNull;
import lombok.Value;

@Value
public class Dependency {

    String groupId;
    String artifactId;
    String version;

    public Dependency withVersion(String newVersion) {
        if (newVersion.equals(version)) {
            return this;
        }
        return new Dependency(groupId,artifactId,newVersion);
    }
}
