package perobobbot.web.api;

import io.micronaut.http.annotation.Get;
import lombok.NonNull;
import perobobbot.api.data.Channel;

import java.util.List;

public interface ChannelApi extends WebService {

    String PATH = ROOT_PATH+"/data/channel";

    @Get("/")
    @NonNull List<Channel> getChannels();


}
