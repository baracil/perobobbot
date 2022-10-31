package perobobbot.api;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class IncompatibleIdentification extends PerobobbotException {
    private final @NonNull Identity id1;
    private final @NonNull Identity id2;

    public IncompatibleIdentification(@NonNull Identity id1, @NonNull Identity id2) {
        super("Incompatible identification id1=" + id1 + " id2=" + id2);
        this.id1 = id1;
        this.id2 = id2;
    }
}
