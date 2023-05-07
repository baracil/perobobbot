package perobobbot.chat.api.irc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;

import java.util.List;

@Value
public class Params {

    List<String> parameters;

    @JsonIgnore
    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    @JsonIgnore
    public String getParameterAt(int index) {
        return parameters.get(index);
    }

    @JsonIgnore
    public String getLastParameter() {
        return getParameterAt(parameters.size()-1);
    }


    public static Params fromFpc(fpc.tools.irc.Params params) {
        return new Params(params.getParameters()) ;
    }
}