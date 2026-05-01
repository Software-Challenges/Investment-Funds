package com.investment.domain.model;

import com.investment.domain.enums.ETransactionType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private UUID id;
    private ETransactionType type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private User user;
    private Subscription subscription;
    private Fund fund;
}
