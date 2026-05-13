package com.investment.application.subscription.dto.response;

import com.investment.domain.enums.EFundCategory;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record FundSummaryResponse(
    UUID id,
    String code,
    String name,
    BigDecimal minimumAmount,
    EFundCategory category
) { }
