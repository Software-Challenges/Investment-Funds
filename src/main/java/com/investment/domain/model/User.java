package com.investment.domain.model;

import com.investment.domain.enums.ENotificationPreference;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private BigDecimal balance;
    private ENotificationPreference notificationPreference;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    public void depositBalance(BigDecimal amount) {
        validateAmount(amount);
        this.balance = this.balance.subtract(amount);
    }

    public void withdrawBalance(BigDecimal amount) {
        validateAmount(amount);
        validateSufficientBalance(amount);
        this.balance = this.balance.add(amount);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            // TODO: Implemented exception here
            throw new RuntimeException("Amount cannot be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            // TODO: Implemented exception here
            throw new RuntimeException("Amount must be greater than zero");
        }
    }

    private void validateSufficientBalance(BigDecimal amount) {
        if (this.balance == null) {
            // TODO: Implemented exception here
            throw new RuntimeException("User balance is not initialized");
        }

        if (amount.compareTo(this.balance) > 0) {
            // TODO: Implemented exception here
            throw new RuntimeException("Insufficient balance");
        }
    }
}
