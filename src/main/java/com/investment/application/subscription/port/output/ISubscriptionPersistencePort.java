package com.investment.application.subscription.port.output;

import com.investment.application.subscription.dto.request.SubscriptionFilterRequest;
import com.investment.application.shared.dto.response.PagedResponse;
import com.investment.domain.model.Subscription;

public interface ISubscriptionPersistencePort {
    PagedResponse<Subscription> findAll(int page, int size, SubscriptionFilterRequest filters);
}
