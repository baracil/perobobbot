package perobobbot.api.data;

import java.util.List;

public class UnmanagedPlatform extends EntityNotFound {

    private final Platform platform;

    public UnmanagedPlatform(Platform platform) {
        super("Platform '"+platform+"' is not managed");
        this.platform = platform;
    }


    @Override
    public List<Criteria> searchedCriteria() {
        return List.of(new Criteria("platform",platform));
    }

}
