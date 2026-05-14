package com.investment.application.subscription.command;

import com.investment.application.fund.port.output.IFundPersistencePort;
import com.investment.application.subscription.dto.request.SubscribeFundRequest;
import com.investment.application.subscription.dto.response.SubscriptionResponse;
import com.investment.application.subscription.port.input.ISubscribeFundUseCase;
import com.investment.application.subscription.port.output.ISubscriptionPersistencePort;
import com.investment.application.subscription.validator.SubscribeFundValidator;
import com.investment.application.user.port.output.IUserPersistencePort;
import com.investment.domain.enums.ESubscriptionStatus;
import com.investment.domain.model.Fund;
import com.investment.domain.model.Subscription;
import com.investment.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscribeFundService implements ISubscribeFundUseCase {
    private final IUserPersistencePort userPort;
    private final IFundPersistencePort fundPort;
    private final ISubscriptionPersistencePort subscriptionPort;
    private final SubscribeFundValidator validator;

    @Override
    public SubscriptionResponse execute(SubscribeFundRequest request) {
        // Validations
        User user = userPort.findById(request.userId())
                            .orElseThrow(() -> new RuntimeException("User not found"));

        Fund fund = fundPort.findById(request.fundId())
                            .orElseThrow(() -> new RuntimeException("Fund not found"));

        validator.validate(request, user, fund);

        // Happy flow
        user.withdrawBalance(request.amount());
        userPort.updateBalance(user);

        Subscription subscription = Subscription.builder()
                                                .user(user)
                                                .fund(fund)
                                                .amount(request.amount())
                                                .status(ESubscriptionStatus.ACTIVE)
                                                .build();

        Subscription savedSubscription = subscriptionPort.createSubscriptionFund(subscription);

        return SubscriptionResponse.builder()
                                   .id(savedSubscription.getId())
                                   .userId(savedSubscription.getUser().getId())
                                   .fundId(savedSubscription.getFund().getId())
                                   .fundName(savedSubscription.getFund().getName())
                                   .amount(savedSubscription.getAmount())
                                   .subscribedAt(savedSubscription.getSubscribedAt())
                                   .cancelledAt(savedSubscription.getCancelledAt())
                                   .build();
    }
}
