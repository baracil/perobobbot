package perobobbot.chat.impl;

import com.google.common.base.Equivalence;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.api.data.Platform;
import perobobbot.api.data.view.UserIdentity;
import perobobbot.chat.api.Chat;
import perobobbot.chat.api.ChatFactory;
import perobobbot.chat.api.NoChatFactoryForPlatform;
import perobobbot.chat.api.PlatformChatFactory;

import java.util.Collection;
import java.util.function.Function;

@RequiredArgsConstructor
public class DefaultChatFactory implements ChatFactory {

    public static @NonNull ChatFactory create(@NonNull Collection<PlatformChatFactory> factories) {
        final var factoryPerPlatform = factories.stream().map(CHAT_FACTORY_EQUIVALENCE::wrap)
                .distinct()
                .map(Equivalence.Wrapper::get)
                .collect(ImmutableMap.toImmutableMap(PlatformChatFactory::getPlatform, Function.identity()));
        return new DefaultChatFactory(factoryPerPlatform);
    }

    private final @NonNull ImmutableMap<Platform, PlatformChatFactory> factories;

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


    private static final Equivalence<PlatformChatFactory> CHAT_FACTORY_EQUIVALENCE = Equivalence.equals().onResultOf(PlatformChatFactory::getPlatform);
}
