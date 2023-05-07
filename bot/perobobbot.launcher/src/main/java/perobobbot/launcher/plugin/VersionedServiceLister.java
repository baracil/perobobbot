package perobobbot.launcher.plugin;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.qualifiers.Qualifiers;
import jplugman.api.VersionedService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import perobobbot.api.plugin.PerobobbotService;
import perobobbot.api.plugin.PerobobbotServices;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionedServiceLister {

    public static Set<VersionedService> list(ApplicationContext applicationContext) {
        return new VersionedServiceLister(applicationContext).list();
    }

    private final ApplicationContext applicationContext;

    public Set<VersionedService> list() {
        final var bean1 = applicationContext.getBeanDefinitions(Qualifiers.byStereotype(PerobobbotService.class));
        final var bean2 = applicationContext.getBeanDefinitions(Qualifiers.byStereotype(PerobobbotServices.class));
        return Stream.concat(
                             bean1.stream(),
                             bean2.stream()
                     ).flatMap(this::extractVersionServices)
                     .collect(Collectors.toSet());
    }

    private Stream<VersionedService> extractVersionServices(BeanDefinition<?> beanDefinition) {
        final var bean = applicationContext.getBean(beanDefinition);
        return extractAnnotations(beanDefinition)
                .map(perobobbotService -> createVersionService(bean, perobobbotService))
                .filter(Objects::nonNull);
    }

    private @Nullable VersionedService createVersionService(Object bean, AnnotationValue<PerobobbotService> perobobbotService) {
        final var apiVersion = perobobbotService.getRequiredValue(PerobobbotService.API_VERSION, Integer.class);
        final var serviceType = perobobbotService.getRequiredValue(PerobobbotService.SERVICE_TYPE, Class.class);

        if (serviceType.isInstance(bean)) {
            return new VersionedService(serviceType, bean, apiVersion);
        }
        return null;

    }

    private Stream<AnnotationValue<PerobobbotService>> extractAnnotations(BeanDefinition<?> definition) {
        final var service = definition.getAnnotation(PerobobbotService.class);
        final var services = definition.getAnnotation(PerobobbotServices.class);

        return Stream.concat(
                Stream.of(service),
                Optional.ofNullable(services)
                        .map(d -> d.getAnnotations("value", PerobobbotService.class))
                        .orElseGet(List::of)
                        .stream()
        ).filter(Objects::nonNull);
    }

}
