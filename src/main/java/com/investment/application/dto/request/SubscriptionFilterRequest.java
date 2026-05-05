package com.investment.application.dto.request;

import com.investment.domain.enums.ESubscriptionStatus;
import lombok.Builder;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record SubscriptionFilterRequest(
        ESubscriptionStatus status,
        UUID userId,
        UUID fundId,
        LocalDate cancelledFrom,
        LocalDate cancelledTo
) {
    public boolean hasFilter() {
        return status != null || userId != null || fundId != null || cancelledFrom != null || cancelledTo != null;
    }
}
