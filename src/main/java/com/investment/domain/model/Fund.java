package com.investment.domain.model;

import com.investment.domain.enums.EFundCategory;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Fund {
    private UUID id;
    private String code;
    private String name;
    private BigDecimal minimumAmount;
    private EFundCategory category;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
