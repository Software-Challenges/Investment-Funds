package com.investment.application.subscription.port.output;

import com.investment.application.subscription.dto.request.SubscriptionFilterRequest;
import com.investment.application.shared.dto.response.PagedResponse;
import com.investment.domain.enums.ESubscriptionStatus;
import com.investment.domain.model.Subscription;
import java.util.Optional;
import java.util.UUID;

public interface ISubscriptionPersistencePort {
    Optional<Subscription> findById(UUID subscriptionId);

    PagedResponse<Subscription> findAll(int page, int size, SubscriptionFilterRequest filters);

    boolean existsByUserIdAndFundIdAndStatus(UUID userId, UUID fundId, ESubscriptionStatus status);

    Subscription createSubscriptionFund(Subscription subscription);
}
