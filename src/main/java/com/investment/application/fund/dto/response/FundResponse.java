package com.investment.application.fund.dto.response;

import com.investment.domain.enums.EFundCategory;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record FundResponse(
        UUID id,
        String code,
        String name,
        BigDecimal minimumAmount,
        EFundCategory category,
        Boolean isActive,
        LocalDateTime createdAt
) { }
