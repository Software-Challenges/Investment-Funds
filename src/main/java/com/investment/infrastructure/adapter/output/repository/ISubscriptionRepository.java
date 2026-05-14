package com.investment.infrastructure.adapter.output.repository;

import com.investment.domain.enums.ESubscriptionStatus;
import com.investment.infrastructure.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;

public interface ISubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID>,
                                                 JpaSpecificationExecutor<SubscriptionEntity> {
    boolean existsByUserIdAndFundIdAndStatus(UUID userId, UUID fundId, ESubscriptionStatus status);
}
