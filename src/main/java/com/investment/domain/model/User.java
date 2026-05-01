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
}
