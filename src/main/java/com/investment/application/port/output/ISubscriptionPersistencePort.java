package com.investment.application.port.output;

import com.investment.application.dto.request.SubscriptionFilterRequest;
import com.investment.application.dto.response.PagedResponse;
import com.investment.domain.model.Subscription;

public interface ISubscriptionPersistencePort {
    PagedResponse<Subscription> findAll(int page, int size, SubscriptionFilterRequest filters);
}
