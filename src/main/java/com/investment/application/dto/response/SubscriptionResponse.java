package com.investment.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SubscriptionResponse(
        UUID id,
        UUID userId,
        UUID fundId,
        String fundName,
        BigDecimal amount,
        ESubscriptionStatus status,
        LocalDateTime subscribedAt,
        LocalDateTime cancelledAt
) { }
