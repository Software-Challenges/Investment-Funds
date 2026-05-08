package com.investment.infrastructure.adapter.output.specification;

import com.investment.application.fund.dto.request.FundFilterRequest;
import com.investment.domain.enums.EFundCategory;
import com.investment.infrastructure.entity.FundEntity;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;

public class FundSpecification {
    private FundSpecification() { }

    public static Specification<FundEntity> withFilters(FundFilterRequest filters) {
        return Specification.where(hasName(filters.name())
                            .and(hasMinimumAmount(filters.minimumAmount()))
                            .and(hasCategory(filters.category()))
                            .and(hasStatus(filters.isActive())));
    }

    private static Specification<FundEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) return null;

            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    private static Specification<FundEntity> hasMinimumAmount(BigDecimal minimumAmount) {
        return (root, query, criteriaBuilder) -> {
            if (minimumAmount == null) return null;

            return criteriaBuilder.equal(root.get("minimumAmount"), minimumAmount);
        };
    }

    private static Specification<FundEntity> hasCategory(EFundCategory category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) return null;

            return criteriaBuilder.equal(root.get("category"), category);
        };
    }

    private static Specification<FundEntity> hasStatus(Boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            if (isActive == null) return null;

            return criteriaBuilder.equal(root.get("isActive"), isActive);
        };
    }
}
