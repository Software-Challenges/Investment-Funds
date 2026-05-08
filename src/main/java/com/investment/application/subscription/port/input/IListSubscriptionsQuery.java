package com.investment.application.subscription.port.input;

import com.investment.application.subscription.dto.request.SubscriptionFilterRequest;
import com.investment.application.shared.dto.response.PagedResponse;
import com.investment.application.subscription.dto.response.SubscriptionResponse;

public interface IListSubscriptionsQuery {
    PagedResponse<SubscriptionResponse> execute(int page, int size, SubscriptionFilterRequest filters);
}
