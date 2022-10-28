package perobobbot.twitch.web.api.eventsub.event;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import perobobbot.twitch.web.api.UserInfo;

import java.time.Instant;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChannelPollProgressEvent extends PollEvent {

    @NonNull Instant endsAt;

    @java.beans.ConstructorProperties({"id", "broadcaster", "title", "choices", "bitsVoting", "channelPointsVoting", "startedAt","endsAt"})
    public ChannelPollProgressEvent(@NonNull String id, @NonNull UserInfo broadcaster, @NonNull String title, @NonNull ImmutableList<PollChoices> choices, @NonNull Voting bitsVoting, @NonNull Voting channelPointsVoting, @NonNull Instant startedAt, @NonNull Instant endsAt) {
        super(id, broadcaster, title, choices, bitsVoting, channelPointsVoting, startedAt);
        this.endsAt = endsAt;
    }

}
