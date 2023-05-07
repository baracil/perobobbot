package perobobbot.api.plugin;

import fpc.tools.fp.Function2;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public abstract class ExtensionPlugin implements Plugin {

    private final Function2<? super ModuleLayer, ? super ServiceProvider, ? extends PerobobbotPlugin> factory;
    @Getter
    private final Set<Requirement<?>> requirements;

    @Override
    public Class<?> getServiceClass() {
        return PerobobbotPlugin.class;
    }

    @Override
    public Object loadService(ModuleLayer pluginLayer, ServiceProvider serviceProvider) {
        return factory.apply(pluginLayer,serviceProvider);
    }

}
