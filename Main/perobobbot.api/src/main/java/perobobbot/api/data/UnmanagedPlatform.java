package perobobbot.api.data;

import lombok.NonNull;

import java.util.List;

public class UnmanagedPlatform extends EntityNotFound {

    private final @NonNull Platform platform;

    public UnmanagedPlatform(@NonNull Platform platform) {
        super("Platform '"+platform+"' is not managed");
        this.platform = platform;
    }


    @Override
    public @NonNull List<Criteria> searchedCriteria() {
        return List.of(new Criteria("platform",platform));
    }

}
