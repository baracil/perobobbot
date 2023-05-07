package perobobbot.api.data;

import lombok.Getter;

import java.util.List;

public class NoMainUserToken extends EntityNotFound {

    @Getter
    private final Platform platform;

    public NoMainUserToken(Platform platform) {
        super("No main user token is defined for platform '"+platform+"'");
        this.platform = platform;
    }

    @Override
    public List<Criteria> searchedCriteria() {
        return List.of(new Criteria("platform",platform));
    }
}
