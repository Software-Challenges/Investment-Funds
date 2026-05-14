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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetSubscriptionByIdService implements IGetSubscriptionByIdQuery {
    private final ISubscriptionPersistencePort subscriptionPort;

    @Override
    public SubscriptionDetailResponse execute(UUID subscriptionId) {
        Subscription susbscription = subscriptionPort.findById(subscriptionId)
                                                     // TODO: Implemented exception here
                                                     .orElseThrow(() -> new RuntimeException("Implemented exception here"));

        return SubscriptionDetailResponse.builder()
                                         .id(susbscription.getId())
                                         .amount(susbscription.getAmount())
                                         .status(susbscription.getStatus())
                                         .subscribedAt(susbscription.getSubscribedAt())
                                         .cancelledAt(susbscription.getCancelledAt())
                                         .user(UserSummaryResponse.builder()
                                                                  .id(susbscription.getId())
                                                                  .fullName(susbscription.getUser().getFullName())
                                                                  .email(susbscription.getUser().getEmail())
                                                                  .build())
                                         .fund(FundSummaryResponse.builder()
                                                                  .id(susbscription.getFund().getId())
                                                                  .code(susbscription.getFund().getCode())
                                                                  .name(susbscription.getFund().getName())
                                                                  .minimumAmount(susbscription.getFund().getMinimumAmount())
                                                                  .category(susbscription.getFund().getCategory())
                                                                  .build())
                                         .build();
    }
}
