package com.investment.infrastructure.entity;

import com.investment.domain.enums.ESubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ESubscriptionStatus status;

    @Column(name = "subscribed_at")
    private LocalDateTime subscribedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_fund_id")
    private FundEntity fund;

    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<TransactionEntity> transactions = new HashSet<>();

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = ESubscriptionStatus.ACTIVE;
        }

        subscribedAt = LocalDateTime.now();
    }
}
