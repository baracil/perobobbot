package perobobbot.twitch.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.oauth.Scope;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum TwitchScope implements Scope {
    ANALYTICS_READ_EXTENSIONS("analytics:read:extensions"),
    ANALYTICS_READ_GAMES("analytics:read:games"),
    BITS_READ("bits:read"),
    CHANNEL_EDIT_COMMERCIAL("channel:edit:commercial"),
    CHANNEL_MANAGE_BROADCAST("channel:manage:broadcast"),
    CHANNEL_READ_CHARITY("channel:read:charity"),
    CHANNEL_MANAGE_EXTENSIONS("channel:manage:extensions"),
    CHANNEL_MANAGE_MODERATORS("channel:manage:moderators"),
    CHANNEL_MANAGE_POLLS("channel:manage:polls"),
    CHANNEL_MANAGE_PREDICTIONS("channel:manage:predictions"),
    CHANNEL_MANAGE_RAIDS("channel:manage:raids"),
    CHANNEL_MANAGE_REDEMPTIONS("channel:manage:redemptions"),
    CHANNEL_MANAGE_SCHEDULE("channel:manage:schedule"),
    CHANNEL_MANAGE_VIDEOS("channel:manage:videos"),
    CHANNEL_READ_EDITORS("channel:read:editors"),
    CHANNEL_READ_GOALS("channel:read:goals"),
    CHANNEL_READ_HYPE_TRAIN("channel:read:hype_train"),
    CHANNEL_READ_POLLS("channel:read:polls"),
    CHANNEL_READ_PREDICTIONS("channel:read:predictions"),
    CHANNEL_READ_REDEMPTIONS("channel:read:redemptions"),
    CHANNEL_READ_STREAM_KEY("channel:read:stream_key"),
    CHANNEL_READ_SUBSCRIPTIONS("channel:read:subscriptions"),
    CHANNEL_READ_VIPS("channel:read:vips"),
    CHANNEL_MANAGE_VIPS("channel:manage:vips"),
    CLIPS_EDIT("clips:edit"),
    MODERATION_READ("moderation:read"),
    MODERATOR_MANAGE_ANNOUNCEMENTS("moderator:manage:announcements"),
    MODERATOR_MANAGE_AUTOMOD("moderator:manage:automod"),
    MODERATOR_READ_AUTOMOD_SETTINGS("moderator:read:automod_settings"),
    MODERATOR_MANAGE_AUTOMOD_SETTINGS("moderator:manage:automod_settings"),
    MODERATOR_MANAGE_BANNED_USERS("moderator:manage:banned_users"),
    MODERATOR_READ_BLOCKED_TERMS("moderator:read:blocked_terms"),
    MODERATOR_MANAGE_BLOCKED_TERMS("moderator:manage:blocked_terms"),
    MODERATOR_MANAGE_CHAT_MESSAGES("moderator:manage:chat_messages"),
    MODERATOR_READ_CHAT_SETTINGS("moderator:read:chat_settings"),
    MODERATOR_MANAGE_CHAT_SETTINGS("moderator:manage:chat_settings"),
    USER_EDIT("user:edit"),
    USER_EDIT_FOLLOWS("user:edit:follows"),
    USER_MANAGE_BLOCKED_USERS("user:manage:blocked_users"),
    USER_READ_BLOCKED_USERS("user:read:blocked_users"),
    USER_READ_BROADCAST("user:read:broadcast"),
    USER_MANAGE_CHAT_COLOR("user:manage:chat_color"),
    USER_READ_EMAIL("user:read:email"),
    USER_READ_FOLLOWS("user:read:follows"),
    USER_READ_SUBSCRIPTIONS("user:read:subscriptions"),
    USER_MANAGE_WHISPERS("user:manage:whispers"),
    CHANNEL_MODERATE("channel:moderate"),
    CHAT_EDIT("chat:edit"),
    CHAT_READ("chat:read"),
    WHISPERS_READ("whispers:read"),
    WHISPERS_EDIT("whispers:edit"),
;
    @Getter
    private final @NonNull String name;

    public static @NonNull Stream<TwitchScope> streamValues() {
        return LazyHolder.VALUES.stream();
    }

    public static @NonNull Optional<TwitchScope> findScopeByName(@NonNull String twitchScopeName) {
        return Optional.ofNullable(LazyHolder.VALUES_BY_NAME.get(twitchScopeName.toLowerCase()));
    }

    public static @NonNull ImmutableSet<TwitchScope> valuesAsSet() {
        return LazyHolder.VALUES;
    }


    private static class LazyHolder {
        private static final ImmutableMap<String, TwitchScope> VALUES_BY_NAME = Arrays.stream(values())
                                                                                      .collect(ImmutableMap.toImmutableMap(s -> s.getName().toLowerCase(), s -> s));
        private static final ImmutableSet<TwitchScope> VALUES = Arrays.stream(values()).collect(ImmutableSet.toImmutableSet());
    }


}
