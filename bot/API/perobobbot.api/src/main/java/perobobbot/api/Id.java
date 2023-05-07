package perobobbot.api;

import perobobbot.api.data.Platform;

public sealed interface Id permits Identity, BotId, IdentityByLogin {

    Platform platform();

    <T> T accept(Visitor<T> visitor);

    interface Visitor<T> {
        T visit(Identity identity);
        T visit(BotId botId);
        T visit(IdentityByLogin identityByLogin);
    }
}
