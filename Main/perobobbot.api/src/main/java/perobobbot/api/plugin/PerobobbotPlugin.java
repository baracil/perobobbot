package perobobbot.api.plugin;

import com.google.common.collect.ImmutableList;
import jplugman.annotation.ExtensionPoint;
import lombok.NonNull;

@ExtensionPoint(version = 1)
public interface PerobobbotPlugin {

    @NonNull String getName();

    @NonNull ImmutableList<PerobobbotPluginData> getPluginData();

}
