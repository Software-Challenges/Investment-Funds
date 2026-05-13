package com.investment.application.subscription.port.input;

import com.investment.application.subscription.dto.response.SubscriptionDetailResponse;
import java.util.UUID;

public interface IGetSubscriptionByIdQuery {
    SubscriptionDetailResponse execute(UUID subscriptionId);
}
