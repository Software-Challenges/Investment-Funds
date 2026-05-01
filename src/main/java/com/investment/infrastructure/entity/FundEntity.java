package com.investment.infrastructure.entity;

import com.investment.domain.enums.EFundCategory;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "funds")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;
    private String name;

    @Column(name = "minimum_amount")
    private BigDecimal minimumAmount;

    @Enumerated(EnumType.STRING)
    private EFundCategory category;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "fund", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<SubscriptionEntity> subscriptions = new HashSet<>();

    @OneToMany(mappedBy = "fund", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<TransactionEntity> transactions = new HashSet<>();

    @PrePersist
    public void prePersist() {
        if (isActive == null) {
            isActive = true;
        }

        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
