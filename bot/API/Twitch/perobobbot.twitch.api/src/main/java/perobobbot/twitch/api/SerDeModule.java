package perobobbot.twitch.api;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fpc.tools.jackson.IdentifiedEnumSerDe;
import jakarta.inject.Singleton;
import perobobbot.twitch.api.serde.ConditionsSerDe;
import perobobbot.twitch.api.serde.PaginationSerDe;
import perobobbot.twitch.api.serde.TwitchApiPayloadDeserModifier;

import java.util.List;

@Singleton
public class SerDeModule extends SimpleModule {

    public SerDeModule() {
        IdentifiedEnumSerDe.addToModule(this, CriteriaType.class);
        IdentifiedEnumSerDe.addToModule(this, RewardRedemptionStatus.class);
        IdentifiedEnumSerDe.addToModule(this, SubscriptionType.class);
        IdentifiedEnumSerDe.addToModule(this, GoalType.class);

        new ConditionsSerDe().register(this);
        new PaginationSerDe().register(this);



        this.setNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.setDeserializerModifier(new TwitchApiPayloadDeserModifier(TwitchApiPayload.class::isAssignableFrom));
    }


    @Override
    public Iterable<? extends Module> getDependencies() {
        return List.of(new GuavaModule(), new Jdk8Module(), new JavaTimeModule());
    }
}
