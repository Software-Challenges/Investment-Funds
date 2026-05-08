package com.investment.application.transaction.dto.response;

import com.investment.domain.enums.ETransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID userId,
        UUID fundId,
        String fundName,
        UUID subscriptionId,
        ETransactionType type,
        BigDecimal amount,
        String description,
        LocalDateTime createdAt
) { }
