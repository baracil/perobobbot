package perobobbot.api;

import lombok.Getter;
import perobobbot.api.data.DataException;

public class SubscriptionNotFound extends DataException {

    @Getter
    private final long id;

    public SubscriptionNotFound(long id) {
        super("Cannot find subscription with id='"+id+"'");
        this.id = id;
    }
}
