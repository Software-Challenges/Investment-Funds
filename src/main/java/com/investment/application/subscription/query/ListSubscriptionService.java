package com.investment.application.subscription.query;

import com.investment.application.subscription.dto.request.SubscriptionFilterRequest;
import com.investment.application.shared.dto.response.PagedResponse;
import com.investment.application.subscription.dto.response.SubscriptionResponse;
import com.investment.application.subscription.port.input.IListSubscriptionsQuery;
import com.investment.application.subscription.port.output.ISubscriptionPersistencePort;
import com.investment.domain.model.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListSubscriptionService implements IListSubscriptionsQuery {
    private final ISubscriptionPersistencePort subscriptionPort;

    @Override
    public PagedResponse<SubscriptionResponse> execute(int page, int size, SubscriptionFilterRequest filters) {
        PagedResponse<Subscription> response = subscriptionPort.findAll(page, size, filters);

        List<SubscriptionResponse> subscriptionResponseList = response.content()
                                                                      .stream()
                                                                      .map(subscription -> new SubscriptionResponse(subscription.getId(),
                                                                                                                               subscription.getUser().getId(),
                                                                                                                               subscription.getFund().getId(),
                                                                                                                               subscription.getFund().getName(),
                                                                                                                               subscription.getAmount(),
                                                                                                                               subscription.getStatus(),
                                                                                                                               subscription.getSubscribedAt(),
                                                                                                                               subscription.getCancelledAt())
                                                                      )
                                                                      .toList();

        return PagedResponse.<SubscriptionResponse>builder()
                            .content(subscriptionResponseList)
                            .page(response.page())
                            .size(response.size())
                            .totalElements(response.totalElements())
                            .totalPages(response.totalPages())
                            .last(response.last())
                            .build();
    }
}
