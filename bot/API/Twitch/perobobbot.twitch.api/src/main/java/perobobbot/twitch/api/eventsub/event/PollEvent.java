package perobobbot.twitch.api.eventsub.event;

import lombok.Value;
import lombok.experimental.NonFinal;
import perobobbot.twitch.api.TwitchApiPayload;
import perobobbot.twitch.api.UserInfo;

import java.time.Instant;
import java.util.List;

@Value
@NonFinal
public abstract class PollEvent implements BroadcasterProvider, EventSubEvent, TwitchApiPayload {

    String id;
    UserInfo broadcaster;
    String title;
    List<PollChoices> choices;
    Voting bitsVoting;
    Voting channelPointsVoting;
    Instant startedAt;

}
