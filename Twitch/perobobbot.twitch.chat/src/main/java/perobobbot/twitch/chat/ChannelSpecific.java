package perobobbot.twitch.chat;

import lombok.NonNull;

public interface ChannelSpecific {

    @NonNull
    TwitchChannel getChannel();
}
