package perobobbot.twitch.api;

import fpc.tools.lang.IdentifiedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.api.eventsub.event.*;
import perobobbot.twitch.api.eventsub.subscription.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * set temp1 to tbody in page <a href="https://dev.twitch.tv/docs/eventsub/eventsub-subscription-types">SubscriptionTypes</a>
 * <p>
 * for(i=0;i<temp1.children.length;i++) {
 * item = temp1.children[i];
 * type = item.children[0].children[0].text.replace(/ /g,"_").toUpperCase();
 * name = item.children[1].textContent;
 * version = item.children[2].textContent;
 * description = item.children[3].textContent;
 * console.log(type+ '("' +name+ '","' + version + '","' + description +'")');
 * }
 */
@RequiredArgsConstructor
public enum SubscriptionType implements IdentifiedEnum {
    CHANNEL_UPDATE("channel.update", "1", ChannelUpdateEvent.class, ChannelUpdate.FACTORY, "A broadcaster updates their channel properties e.g., category, title, mature flag, broadcast, or language."),
    CHANNEL_FOLLOW("channel.follow", "1", ChannelFollowEvent.class, ChannelFollow.FACTORY, "A specified channel receives a follow."),
    CHANNEL_SUBSCRIBE("channel.subscribe", "1", SubscribeEvent.class, ChannelSubscribe.FACTORY, "A notification when a specified channel receives a subscriber. This does not include resubscribes."),
    CHANNEL_SUBSCRIPTION_END("channel.subscription.end", "1", SubscriptionEndEvent.class, ChannelSubscriptionEnd.FACTORY, "A notification when a subscription to the specified channel ends."),
    CHANNEL_SUBSCRIPTION_GIFT("channel.subscription.gift", "1", SubscriptionGiftEvent.class, ChannelSubscriptionGift.FACTORY, "A notification when a viewer gives a gift subscription to one or more users in the specified channel."),
    CHANNEL_SUBSCRIPTION_MESSAGE("channel.subscription.message", "1", SubscriptionMessageEvent.class, ChannelSubscriptionMessage.FACTORY, "A notification when a user sends a resubscription chat message in a specific channel."),
    CHANNEL_CHEER("channel.cheer", "1", CheerEvent.class, ChannelCheer.FACTORY, "A user cheers on the specified channel."),
    CHANNEL_RAID("channel.raid", "1", ChannelRaidEvent.class, ChannelRaid.FACTORY, "A broadcaster raids another broadcaster’s channel."),
    CHANNEL_BAN("channel.ban", "1", ChannelBanEvent.class, ChannelBan.FACTORY, "A viewer is banned from the specified channel."),
    CHANNEL_UNBAN("channel.unban", "1", ChannelUnbanEvent.class, ChannelUnban.FACTORY, "A viewer is unbanned from the specified channel."),
    CHANNEL_MODERATOR_ADD("channel.moderator.add", "1", ChannelModeratorAddEvent.class, ChannelModeratorAdd.FACTORY, "Moderator privileges were added to a user on a specified channel."),
    CHANNEL_MODERATOR_REMOVE("channel.moderator.remove", "1", ChannelModeratorRemoveEvent.class, ChannelModeratorRemove.FACTORY, "Moderator privileges were removed from a user on a specified channel."),
    CHANNEL_POINTS_CUSTOM_REWARD_ADD("channel.channel_points_custom_reward.add", "1", ChannelPointsCustomRewardAddEvent.class, ChannelPointsCustomRewardAdd.FACTORY, "A custom channel points reward has been created for the specified channel."),
    CHANNEL_POINTS_CUSTOM_REWARD_UPDATE("channel.channel_points_custom_reward.update", "1", ChannelPointsCustomRewardUpdateEvent.class, ChannelPointsCustomRewardUpdate.FACTORY, "A custom channel points reward has been updated for the specified channel."),
    CHANNEL_POINTS_CUSTOM_REWARD_REMOVE("channel.channel_points_custom_reward.remove", "1", ChannelPointsCustomRewardRemoveEvent.class, ChannelPointsCustomRewardRemove.FACTORY, "A custom channel points reward has been removed from the specified channel."),
    CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_ADD("channel.channel_points_custom_reward_redemption.add", "1", ChannelPointsCustomRewardRedemptionAddEvent.class, ChannelPointsCustomRewardRedemptionAdd.FACTORY, "A viewer has redeemed a custom channel points reward on the specified channel."),
    CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_UPDATE("channel.channel_points_custom_reward_redemption.update", "1", ChannelPointsCustomRewardRedemptionUpdateEvent.class, ChannelPointsCustomRewardRedemptionUpdate.FACTORY, "A redemption of a channel points custom reward has been updated for the specified channel."),
    CHANNEL_POLL_BEGIN("channel.poll.begin", "1", ChannelPollBeginEvent.class, ChannelPollBegin.FACTORY, "A poll started on a specified channel."),
    CHANNEL_POLL_PROGRESS("channel.poll.progress", "1", ChannelPollProgressEvent.class, ChannelPollProgress.FACTORY, "Users respond to a poll on a specified channel."),
    CHANNEL_POLL_END("channel.poll.end", "1", PollEndEvent.class, ChannelPollEnd.FACTORY, "A poll ended on a specified channel."),
    CHANNEL_PREDICTION_BEGIN("channel.prediction.begin", "1", PredictionBeginEvent.class, ChannelPredictionBegin.FACTORY, "A Prediction started on a specified channel."),
    CHANNEL_PREDICTION_PROGRESS("channel.prediction.progress", "1", PredictionProgressEvent.class, ChannelPredictionProgress.FACTORY, "Users participated in a Prediction on a specified channel."),
    CHANNEL_PREDICTION_LOCK("channel.prediction.lock", "1", PredictionLockEvent.class, ChannelPredictionLock.FACTORY, "A Prediction was locked on a specified channel."),
    CHANNEL_PREDICTION_END("channel.prediction.end", "1", ChannelPredictionEndEvent.class, ChannelPredictionEnd.FACTORY, "A Prediction ended on a specified channel."),
    CHANNEL_CHARITY_CAMPAIGN_DONATE("channel.charity_campaign.donate", "beta", ChannelCharityCampaignDonateEvent.class, ChannelCharityCampaignDonate.FACTORY, "Sends an event notification when a user donates to the broadcaster’s charity campaign."),
    DROP_ENTITLEMENT_GRANT("drop.entitlement.grant", "1", DropEntitlementGrantEvent.class, DropEntitlementGrant.FACTORY, "An entitlement for a Drop is granted to a user."),
    EXTENSION_BITS_TRANSACTION_CREATE("extension.bits_transaction.create", "1", ExtensionBitsTransactionCreateEvent.class, ExtensionBitsTransactionCreate.FACTORY, "A Bits transaction occurred for a specified Twitch Extension."),
    CHANNEL_GOAL_BEGIN("channel.goal.begin", "1", GoalEvent.class, GoalBegin.FACTORY, "Get notified when a broadcaster begins a goal."),
    CHANNEL_GOAL_PROGRESS("channel.goal.progress", "1", GoalEvent.class, GoalProgress.FACTORY, "Get notified when progress (either positive or negative) is made towards a broadcaster’s goal."),
    CHANNEL_GOAL_END("channel.goal.end", "1", GoalEvent.class, GoalEnd.FACTORY, "Get notified when a broadcaster ends a goal."),
    CHANNEL_HYPE_TRAIN_BEGIN("channel.hype_train.begin", "1", HypeTrainBeginEvent.class, HypeTrainBegin.FACTORY, "A Hype Train begins on the specified channel."),
    CHANNEL_HYPE_TRAIN_PROGRESS("channel.hype_train.progress", "1", HypeTrainProgressEvent.class, HypeTrainProgress.FACTORY, "A Hype Train makes progress on the specified channel."),
    CHANNEL_HYPE_TRAIN_END("channel.hype_train.end", "1", HypeTrainEndEvent.class, HypeTrainEnd.FACTORY, "A Hype Train ends on the specified channel."),
    STREAM_ONLINE("stream.online", "1", StreamOnlineEvent.class, StreamOnline.FACTORY, "The specified broadcaster starts a stream."),
    STREAM_OFFLINE("stream.offline", "1", StreamOfflineEvent.class, StreamOffline.FACTORY, "The specified broadcaster stops a stream."),
    USER_AUTHORIZATION_GRANT("user.authorization.grant", "1", UserAuthorizationGrantEvent.class, UserAuthorizationGrant.FACTORY, "A user’s authorization has been granted to your client id."),
    USER_AUTHORIZATION_REVOKE("user.authorization.revoke", "1", UserAuthorizationRevokeEvent.class, UserAuthorizationRevoke.FACTORY, "A user’s authorization has been revoked for your client id."),
    USER_UPDATE("user.update", "1", UserUpdateEvent.class, UserUpdate.FACTORY, "A user has updated their account."),
    ;
    @Getter
    private final String identification;
    @Getter
    private final String version;

    @Getter
    private final Class<? extends EventSubEvent> eventType;

    private final SubscriptionFactory subscriptionFactory;

    @Getter
    private final String description;

    public Subscription create(Conditions conditions) {
        return subscriptionFactory.create(conditions);
    }


    public static Set<String> getIdentifications() {
        return Holder.VALUE_IDENTIFICATIONS;
    }

    private static class Holder {

        private static final Set<String> VALUE_IDENTIFICATIONS;

        static {
            VALUE_IDENTIFICATIONS = Arrays.stream(values())
                                          .map(SubscriptionType::getIdentification)
                                          .collect(Collectors.toSet());
        }
    }

}
