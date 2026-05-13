package com.investment.application.subscription.dto.response;

import com.investment.domain.enums.ESubscriptionStatus;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record SubscriptionDetailResponse(
        UUID id,
        BigDecimal amount,
        ESubscriptionStatus status,
        LocalDateTime subscribedAt,
        LocalDateTime cancelledAt,
        UserSummaryResponse user,
        FundSummaryResponse fund
) { }
