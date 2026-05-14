package com.investment.application.subscription.port.input;

import com.investment.application.subscription.dto.request.SubscribeFundRequest;
import com.investment.application.subscription.dto.response.SubscriptionResponse;

public interface ISubscribeFundUseCase {
    SubscriptionResponse execute(SubscribeFundRequest request);
}
