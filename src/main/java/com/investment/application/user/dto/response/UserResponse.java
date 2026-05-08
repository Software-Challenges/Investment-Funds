package com.investment.application.user.dto.response;

import com.investment.domain.enums.ENotificationPreference;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String fullName,
        String phoneNumber,
        String email,
        BigDecimal balance,
        ENotificationPreference notificationPreference,
        Boolean isActive,
        Set<String> roles
) { }
