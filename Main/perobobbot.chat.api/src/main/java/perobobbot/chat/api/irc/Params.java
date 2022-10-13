package perobobbot.chat.api.irc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import io.micronaut.serde.annotation.Serdeable;
import lombok.NonNull;
import lombok.Value;

@Value
@Serdeable
public class Params {

    @NonNull ImmutableList<String> parameters;

    @JsonIgnore
    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    @NonNull
    @JsonIgnore
    public String getParameterAt(int index) {
        return parameters.get(index);
    }

    @NonNull
    @JsonIgnore
    public String getLastParameter() {
        return getParameterAt(parameters.size()-1);
    }


    public static @NonNull Params fromFpc(@NonNull fpc.tools.irc.Params params) {
        return new Params(params.getParameters()) ;
    }
}