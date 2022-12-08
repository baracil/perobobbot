package perobobbot.api.plugin;

import jplugman.annotation.ExtensionPoint;
import lombok.NonNull;

@ExtensionPoint(version = 1)
public interface PerobobbotPlugin {

    @NonNull String getName();

    void onPluginStarted();

    void onPluginStopped();

}
