package com.investment.application.subscription.validator;

import com.investment.application.subscription.dto.request.SubscribeFundRequest;
import com.investment.application.subscription.port.output.ISubscriptionPersistencePort;
import com.investment.domain.enums.ESubscriptionStatus;
import com.investment.domain.model.Fund;
import com.investment.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscribeFundValidator {
    private final ISubscriptionPersistencePort subscriptionPort;

    public void validate(SubscribeFundRequest request, User user, Fund fund) {
        validateUserIsActive(user);
        validateFundIsActive(fund);
        validateDuplicateSubscription(request);
        validateMinimumAmount(request, fund);
        validateSufficientBalance(request, user);
    }

    private void validateUserIsActive(User user) {
        if (!Boolean.TRUE.equals(user.getIsActive())) {
            // TODO: Implemented exception here
            throw new RuntimeException("User is not active");
        }
    }

    private void validateFundIsActive(Fund fund) {
        if (!Boolean.TRUE.equals(fund.getIsActive())) {
            // TODO: Implemented exception here
            throw new RuntimeException("Fund is not active");
        }
    }

    private void validateDuplicateSubscription(SubscribeFundRequest request) {
        boolean alreadySubscribed = subscriptionPort.existsByUserIdAndFundIdAndStatus(request.userId(),
                                                                                      request.fundId(),
                                                                                      ESubscriptionStatus.ACTIVE);


        if (alreadySubscribed) {
            // TODO: Implemented exception here
            throw new RuntimeException("Implemented exceptions here");
        }
    }

    private void validateMinimumAmount(SubscribeFundRequest request, Fund fund) {
        if (request.amount().compareTo(fund.getMinimumAmount()) < 0) {
            // TODO: Implemented exception here
            throw new RuntimeException("Implemented exception here");
        }
    }

    private void validateSufficientBalance(SubscribeFundRequest request, User user) {
        if (request.amount().compareTo(user.getBalance()) > 0) {
            // TODO: Implemented exception here
            throw new RuntimeException("Implemented exception here");
        }
    }
}
