package perobobbot.service.jpa;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.SerDeHelper;
import perobobbot.api.SubscriptionManager;
import perobobbot.api.SubscriptionView;
import perobobbot.api.data.Platform;
import perobobbot.domain.jpa.entity.SubscriptionEntity;
import perobobbot.domain.jpa.repository.SubscriptionRepository;
import perobobbot.service.api.CreateSubscriptionParameters;
import perobobbot.service.api.PatchSubscriptionParameters;
import perobobbot.service.api.SubscriptionService;
import perobobbot.service.api.UpdateSubscriptionParameters;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Singleton
@Slf4j
public class JpaSubscriptionService implements SubscriptionService {

    private final SubscriptionManager subscriptionManager;
    private final SubscriptionRepository subscriptionRepository;
    private final SerDeHelper serDeHelper;

    @Override
    public List<SubscriptionView> listSubscriptionsOnPlatform(Platform platform, int page, int size) {
        final var p = subscriptionRepository.findByPlatform(platform, Pageable.from(page, size));
        return toView(p);
    }

    @Override
    public List<SubscriptionView> listSubscriptions(int page, int size) {
        final var p = subscriptionRepository.findAll(Pageable.from(page, size));
        return toView(p);
    }

    private List<SubscriptionView> toView(Page<SubscriptionEntity> page) {
        return page.getContent().stream()
                   .map(s -> s.toView(serDeHelper))
                   .toList();
    }

    @Override
    public Optional<SubscriptionView> deleteSubscription(long id) {
        final var sub = subscriptionRepository.findById(id);
        sub.ifPresent(subscriptionRepository::delete);
        return sub.map(s -> s.toView(serDeHelper));
    }

    @Override
    public SubscriptionView updateSubscription(long id, UpdateSubscriptionParameters parameters) {
        LOG.info("Update subscription id='{}' with '{}'", id, parameters);
        final var subscription = subscriptionRepository.getById(id);
        subscription.update(parameters.getSubscriptionId(), parameters.getCallbackUrl());
        return subscriptionRepository.update(subscription).toView(serDeHelper);
    }

    @Override
    public SubscriptionView patchSubscription(long id, PatchSubscriptionParameters parameters) {
        final var subscription = subscriptionRepository.getById(id);
        parameters.getEnabled().ifPresent(subscription::setEnabled);
        return subscriptionRepository.save(subscription).toView(serDeHelper);
    }

    @Override
    public SubscriptionView createSubscription(CreateSubscriptionParameters parameters) {
        final var subscription = new SubscriptionEntity(
                parameters.getPlatform(),
                parameters.getSubscriptionType(),
                serDeHelper.serializeMap(parameters.getConditions()),
                subscriptionManager.getCallbackUrl(parameters.getPlatform()),
                parameters.isEnabled()
        );
        return subscriptionRepository.save(subscription).toView(serDeHelper);
    }

    @Override
    public SubscriptionView getSubscription(long id) {
        return subscriptionRepository.getById(id).toView(serDeHelper);
    }
}
