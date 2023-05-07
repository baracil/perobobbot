package perobobbot.api.plugin;

import jplugman.annotation.ExtensionPoint;

@ExtensionPoint(version = 1)
public interface PerobobbotPlugin {

    String getName();

    void onPluginStarted();

    void onPluginStopped();

}
