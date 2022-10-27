package perobobbot.twitch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.NonNull;
import perobobbot.twitch.api.channel.ChannelInformation;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.HashMap;
import java.util.List;

public class SerDeBase {

    protected static PodamFactory PODAM_FACTORY = new PodamFactoryImpl();
    protected static ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new SerDeModule());

    public static MapType constructMapType(@NonNull Class<?> valueType) {
        return OBJECT_MAPPER.getTypeFactory().constructMapType(HashMap.class,String.class,valueType);
    }

    public static List<Class<?>> CLASSES = List.of(
            Conditions.class,
            Image.class,
            Transport.class,
            CriteriaType.class,
            RewardRedemptionStatus.class,
            SubscriptionType.class,
            TwitchScope.class,
            ChannelInformation.class);


}
