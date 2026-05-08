package com.investment.application.fund.dto.request;

import com.investment.domain.enums.EFundCategory;
import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record FundFilterRequest(
        String name,
        BigDecimal minimumAmount,
        EFundCategory category,
        Boolean isActive
) {
    public boolean hasFilter() {
        return name != null || minimumAmount != null || category != null || isActive != null;
    }
}
