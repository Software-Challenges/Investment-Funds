package com.investment.application.port.input;

import com.investment.application.dto.request.SubscriptionFilterRequest;
import com.investment.application.dto.response.PagedResponse;
import com.investment.application.dto.response.SubscriptionResponse;

public interface IListSubscriptionsUseCase {
    PagedResponse<SubscriptionResponse> execute(int page, int size, SubscriptionFilterRequest filters);
}
