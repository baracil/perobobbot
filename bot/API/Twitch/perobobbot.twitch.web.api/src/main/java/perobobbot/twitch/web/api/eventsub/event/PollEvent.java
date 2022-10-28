package perobobbot.twitch.web.api.eventsub.event;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import perobobbot.twitch.web.api.UserInfo;

import java.time.Instant;

@Value
@NonFinal
public abstract class PollEvent implements BroadcasterProvider, EventSubEvent {

    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull String title;
    @NonNull ImmutableList<PollChoices> choices;
    @NonNull Voting bitsVoting;
    @NonNull Voting channelPointsVoting;
    @NonNull Instant startedAt;

}
