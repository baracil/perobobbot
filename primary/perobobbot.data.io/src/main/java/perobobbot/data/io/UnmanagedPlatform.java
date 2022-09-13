package perobobbot.data.io;

import lombok.NonNull;

public class UnmanagedPlatform extends EntityNotFound {

    private final @NonNull Platform platform;

    public UnmanagedPlatform(@NonNull Platform platform) {
        super("Platform '"+platform+"' is not managed");
        this.platform = platform;
    }

    @Override
    public @NonNull String searchedCriteria() {
        return "platform";
    }

    @Override
    public @NonNull String searchedValue() {
        return platform.name();
    }
}
