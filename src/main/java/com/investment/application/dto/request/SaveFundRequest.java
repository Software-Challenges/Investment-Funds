package com.investment.application.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record SaveFundRequest(
        @NotBlank(message = "Code is required")
        String code,

        @NotBlank(message = "Name is required")
        String name,

        @NotNull
        @DecimalMin(value = "0.01", message = "Minimum amount must be greater than zero")
        BigDecimal minimumAmount,

        @NotBlank(message = "Category is required")
        String category
) { }
