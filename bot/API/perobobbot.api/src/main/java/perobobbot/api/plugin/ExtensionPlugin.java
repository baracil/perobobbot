package perobobbot.api.plugin;

import fpc.tools.fp.Function2;
import jplugman.api.Plugin;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public abstract class ExtensionPlugin implements Plugin {

    private final @NonNull Function2<? super ModuleLayer, ? super ServiceProvider, ? extends PerobobbotPlugin> factory;
    @Getter
    private final @NonNull Set<Requirement<?>> requirements;

    @Override
    public @NonNull Class<?> getServiceClass() {
        return PerobobbotPlugin.class;
    }

    @Override
    public @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        return factory.apply(pluginLayer,serviceProvider);
    }

}
