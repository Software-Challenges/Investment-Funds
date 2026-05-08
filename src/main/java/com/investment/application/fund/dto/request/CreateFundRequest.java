package com.investment.application.fund.dto.request;

import com.investment.domain.enums.EFundCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateFundRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull
        @DecimalMin(value = "0.01", message = "Minimum amount must be greater than zero")
        BigDecimal minimumAmount,

        @NotNull(message = "Category is required")
        EFundCategory category
) { }
