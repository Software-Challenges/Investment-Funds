package com.investment.domain.model;

import com.investment.domain.enums.ESubscriptionStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    private UUID id;
    private BigDecimal amount;
    private ESubscriptionStatus status;
    private LocalDateTime subscribedAt;
    private LocalDateTime cancelledAt;
    private User user;
    private Fund fund;
}
