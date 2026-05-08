package com.investment.infrastructure.adapter.output.specification;

import com.investment.application.subscription.dto.request.SubscriptionFilterRequest;
import com.investment.domain.enums.ESubscriptionStatus;
import com.investment.infrastructure.entity.SubscriptionEntity;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class SubscriptionSpecification {
    private SubscriptionSpecification() { }

    public static Specification<SubscriptionEntity> withFilters(SubscriptionFilterRequest filters) {
        return Specification.where(hasStatus(filters.status()))
                            .and(hasUser(filters.userId()))
                            .and(hasFund(filters.fundId()))
                            .and(cancelledAtBetween(filters.cancelledFrom(), filters.cancelledTo()));
    }

    private static Specification<SubscriptionEntity> hasStatus(ESubscriptionStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return null;

            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    private static Specification<SubscriptionEntity> hasUser(UUID userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) return null;

            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }

    private static Specification<SubscriptionEntity> hasFund(UUID fundId) {
        return (root, query, criteriaBuilder) -> {
            if (fundId == null) return null;

            return criteriaBuilder.equal(root.get("fund").get("id"), fundId);
        };
    }

    private static Specification<SubscriptionEntity> cancelledAtBetween(LocalDate cancelledFrom, LocalDate cancelledTo) {
        return (root, query, criteriaBuilder) -> {
            if (cancelledFrom == null && cancelledTo == null) return null;

            if (cancelledFrom != null && cancelledTo != null) {
                return criteriaBuilder.between(root.get("cancelledAt"),
                                               cancelledFrom.atStartOfDay(),
                                               cancelledTo.atTime(LocalTime.MAX));
            }

            if (cancelledFrom != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("cancelledAt"), cancelledFrom.atStartOfDay());
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("cancelledAt"), cancelledTo.atTime(LocalTime.MAX));
        };
    }
}
