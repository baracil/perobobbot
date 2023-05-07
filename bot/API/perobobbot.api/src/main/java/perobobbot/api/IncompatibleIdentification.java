package perobobbot.api;

import lombok.Getter;

@Getter
public class IncompatibleIdentification extends PerobobbotException {
    private final Identity id1;
    private final Identity id2;

    public IncompatibleIdentification(Identity id1, Identity id2) {
        super("Incompatible identification id1=" + id1 + " id2=" + id2);
        this.id1 = id1;
        this.id2 = id2;
    }
}
