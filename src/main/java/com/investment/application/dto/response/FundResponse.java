package com.investment.application.dto.response;

import com.investment.domain.enums.EFundCategory;
import java.math.BigDecimal;
import java.util.UUID;

public record FundResponse(
        UUID id,
        String code,
        String name,
        BigDecimal minimumAmount,
        EFundCategory category,
        Boolean isActive
) { }
