package com.investment.infrastructure.adapter.output.repository;

import com.investment.infrastructure.entity.FundEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IFundRepository extends JpaRepository<FundEntity, UUID> {
    Page<FundEntity> findByIsActive(Boolean isActive, Pageable pageable);
}
