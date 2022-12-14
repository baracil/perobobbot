package perobobbot.twitch.chat.message.from;

import fpc.tools.irc.IRCParsing;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.TwitchChannel;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.TagKey;

/**
 * @author Bastien Aracil
 **/
@Getter
@ToString
public class RoomState extends KnownMessageFromTwitch implements ChannelSpecific {

    private final TwitchChannel channel;

    private final boolean emoteOnly;

    @NonNull
    private final FollowMode followMode;

    private final boolean r9kMode;

    private final int slow;

    private final boolean subsOnly;

    @Builder
    public RoomState(@NonNull IRCParsing ircParsing, TwitchChannel channel, boolean emoteOnly, @NonNull FollowMode followMode, boolean r9kMode, int slow, boolean subsOnly) {
        super(ircParsing);
        this.channel = channel;
        this.emoteOnly = emoteOnly;
        this.followMode = followMode;
        this.r9kMode = r9kMode;
        this.slow = slow;
        this.subsOnly = subsOnly;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.ROOMSTATE;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static RoomState build(@NonNull AnswerBuilderHelper helper) {
        return RoomState.builder()
                        .ircParsing(helper.getIrcParsing())
                        .channel(helper.channelFromParameterAt(0))
                        .emoteOnly(helper.tagValueAsBoolean(TagKey.EMOTE_ONLY,false))
                        .followMode(FollowMode.create(helper.tagValueAsInt(TagKey.FOLLOWERS_ONLY, -1)))
                        .r9kMode(helper.tagValueAsBoolean(TagKey.R9K,false))
                        .slow(helper.tagValueAsInt(TagKey.SLOW, 0))
                        .subsOnly(helper.tagValueAsBoolean(TagKey.SUBS_ONLY,false))
                        .build();
    }
}
