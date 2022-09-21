package perobobbot.service.jpa.domain;

import fpc.tools.hibernate.ImmutableScalarStringUserType;
import perobobbot.api.data.Platform;

public class PlatformType extends ImmutableScalarStringUserType<Platform> {

    public PlatformType() {
        super(Platform.class, Platform::name, Platform::new);
    }
}
