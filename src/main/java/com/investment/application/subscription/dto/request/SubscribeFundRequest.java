package com.investment.application.subscription.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record SubscribeFundRequest(
        @NotNull
        UUID userId,

        @NotNull
        UUID fundId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount
) { }
