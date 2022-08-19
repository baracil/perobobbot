package perobobbot.server.io.view;

import lombok.NonNull;

public record CustomerView(@NonNull Long id, @NonNull String firstName, @NonNull String lastName) {

}
