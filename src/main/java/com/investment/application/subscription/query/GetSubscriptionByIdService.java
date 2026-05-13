package com.investment.application.subscription.query;

import com.investment.application.subscription.dto.response.FundSummaryResponse;
import com.investment.application.subscription.dto.response.SubscriptionDetailResponse;
import com.investment.application.subscription.dto.response.UserSummaryResponse;
import com.investment.application.subscription.port.input.IGetSubscriptionByIdQuery;
import com.investment.application.subscription.port.output.ISubscriptionPersistencePort;
import com.investment.domain.model.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetSubscriptionByIdService implements IGetSubscriptionByIdQuery {
    private final ISubscriptionPersistencePort subscriptionPort;

    @Override
    public SubscriptionDetailResponse execute(UUID subscriptionId) {
        Optional<Subscription> subsFound = subscriptionPort.findById(subscriptionId);

        if (subsFound.isEmpty()) {
            throw new RuntimeException("Subscription not found exception");
        }

        return SubscriptionDetailResponse.builder()
                                         .id(subsFound.get().getId())
                                         .amount(subsFound.get().getAmount())
                                         .status(subsFound.get().getStatus())
                                         .subscribedAt(subsFound.get().getSubscribedAt())
                                         .cancelledAt(subsFound.get().getCancelledAt())
                                         .user(UserSummaryResponse.builder()
                                                                  .id(subsFound.get().getId())
                                                                  .fullName(subsFound.get().getUser().getFullName())
                                                                  .email(subsFound.get().getUser().getEmail())
                                                                  .build())
                                         .fund(FundSummaryResponse.builder()
                                                                  .id(subsFound.get().getFund().getId())
                                                                  .code(subsFound.get().getFund().getCode())
                                                                  .name(subsFound.get().getFund().getName())
                                                                  .minimumAmount(subsFound.get().getFund().getMinimumAmount())
                                                                  .category(subsFound.get().getFund().getCategory())
                                                                  .build())
                                         .build();
    }
}
