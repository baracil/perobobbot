package perobobbot.api.data;

import lombok.Getter;

import java.util.List;

public class ApplicationForPlatformMissing extends EntityNotFound {

    @Getter
    private final Platform platform;

    public ApplicationForPlatformMissing(Platform platform) {
        super("No application for the platform '"+ platform.name() +"' exists.");
        this.platform = platform;
    }


    @Override
    public List<Criteria> searchedCriteria() {
        return List.of(new Criteria("platform",platform));
    }
}
