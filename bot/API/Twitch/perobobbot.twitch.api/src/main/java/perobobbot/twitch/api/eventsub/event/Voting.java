package perobobbot.twitch.api.eventsub.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import lombok.Value;

@Value
@Serdeable(naming = SnakeCaseStrategy.class)
public class Voting {

    @JsonAlias("is_enabled")
    boolean enabled;

    @JsonAlias("amount_per_vote")
    int amountPerVote;
}
