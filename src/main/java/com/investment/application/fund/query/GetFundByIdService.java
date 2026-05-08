package com.investment.application.fund.query;

import com.investment.application.fund.dto.response.FundResponse;
import com.investment.application.fund.port.input.IGetFundByIdQuery;
import com.investment.application.fund.port.output.IFundPersistencePort;
import com.investment.domain.model.Fund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetFundByIdService implements IGetFundByIdQuery {
    private final IFundPersistencePort fundPort;

    @Override
    public FundResponse execute(UUID fundId) {
        Optional<Fund> fundFound = fundPort.findById(fundId);

        if (fundFound.isEmpty()) {
            throw new RuntimeException("Found not fund");
        }

        return FundResponse.builder()
                           .id(fundFound.get().getId())
                           .code(fundFound.get().getCode())
                           .name(fundFound.get().getName())
                           .minimumAmount(fundFound.get().getMinimumAmount())
                           .category(fundFound.get().getCategory())
                           .isActive(fundFound.get().getIsActive())
                           .createdAt(fundFound.get().getCreatedAt())
                           .build();
    }
}
