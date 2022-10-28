package perobobbot.service.api;

import lombok.Getter;
import perobobbot.api.data.Platform;

@Getter
public class IncompatiblePlatform extends DomainException {

    private final Platform platform1;
    private final Platform platform2;

    public IncompatiblePlatform(Platform platform1, Platform platform2) {
        super("Incompatible platform '"+platform1+"' != '"+platform2+"'");
        this.platform1 = platform1;
        this.platform2 = platform2;
    }
}
