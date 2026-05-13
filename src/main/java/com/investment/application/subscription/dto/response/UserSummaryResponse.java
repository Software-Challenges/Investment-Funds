package com.investment.application.subscription.dto.response;

import lombok.Builder;
import java.util.UUID;

@Builder
public record UserSummaryResponse(
        UUID id,
        String fullName,
        String email
) { }
