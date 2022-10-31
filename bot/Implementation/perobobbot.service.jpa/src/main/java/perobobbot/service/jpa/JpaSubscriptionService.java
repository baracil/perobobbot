package perobobbot.service.jpa;

import com.google.common.collect.ImmutableList;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.api.SerDeHelper;
import perobobbot.api.SubscriptionView;
import perobobbot.api.data.Platform;
import perobobbot.domain.jpa.entity.SubscriptionEntity;
import perobobbot.domain.jpa.repository.SubscriptionRepository;
import perobobbot.service.api.CreateSubscriptionParameters;
import perobobbot.service.api.SubscriptionService;
import perobobbot.service.api.UpdateSubscriptionParameters;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Singleton
public class JpaSubscriptionService implements SubscriptionService {

    private final @NonNull SubscriptionRepository subscriptionRepository;
    private final @NonNull SerDeHelper serDeHelper;

    @Override
    public @NonNull ImmutableList<SubscriptionView> listSubscriptions(@NonNull Platform platform) {
        return subscriptionRepository.findByPlatform(platform)
                                     .map(s -> s.toView(serDeHelper))
                                     .collect(ImmutableList.toImmutableList());
    }

    @Override
    public void deleteSubscription(long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public @NonNull SubscriptionView updateSubscription(long id, @NonNull UpdateSubscriptionParameters parameters) {
        final var subscription = subscriptionRepository.getById(id);
        subscription.setSubscriptionId(parameters.getSubscriptionId());
        subscription.setSubscriptionId(parameters.getCallbackUrl());
        return subscriptionRepository.update(subscription).toView(serDeHelper);
    }

    @Override
    public @NonNull SubscriptionView createSubscription(@NonNull CreateSubscriptionParameters parameters) {
        final var subscription = new SubscriptionEntity(
                parameters.getPlatform(),
                parameters.getSubscriptionType(),
                serDeHelper.serializeMap(parameters.getConditions()),
                parameters.getCallbackUrl()
                );
        return subscriptionRepository.save(subscription).toView(serDeHelper);
    }
}
