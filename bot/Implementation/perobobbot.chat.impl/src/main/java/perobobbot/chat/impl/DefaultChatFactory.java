package perobobbot.chat.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.api.data.Platform;
import perobobbot.api.data.UserIdentity;
import perobobbot.chat.api.Chat;
import perobobbot.chat.api.ChatFactory;
import perobobbot.chat.api.NoChatFactoryForPlatform;
import perobobbot.chat.api.PlatformChatFactory;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultChatFactory implements ChatFactory {

    private static final Collector<PlatformChatFactory, ?, Map<Platform,PlatformChatFactory>> BY_PLATFORM_COLLECTOR =
            Collectors.toMap(
                    PlatformChatFactory::getPlatform,
                    Function.identity(),
                    (p1,p2) -> p1
            );

    public static @NonNull ChatFactory create(@NonNull Collection<PlatformChatFactory> factories) {
        final var factoryPerPlatform = factories.stream().collect(BY_PLATFORM_COLLECTOR);
        return new DefaultChatFactory(factoryPerPlatform);
    }

    private final @NonNull Map<Platform, PlatformChatFactory> factories;

    @Override
    @Synchronized
    public @NonNull Chat create(@NonNull UserIdentity userIdentity) throws InterruptedException {
        final var factory = getFactory(userIdentity.platform());
        final var chat = factory.create(userIdentity);
        chat.connect();
        return chat;
    }

    private @NonNull PlatformChatFactory getFactory(@NonNull Platform platform) {
        final var factory = factories.get(platform);
        if (factory == null) {
            throw new NoChatFactoryForPlatform(platform);
        }
        return factory;
    }

}
