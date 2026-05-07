package com.investment.application.usecase;

import com.investment.application.dto.request.UpdateFundRequest;
import com.investment.application.dto.response.FundResponse;
import com.investment.application.port.input.IUpdateFundUseCase;
import com.investment.application.port.output.IFundPersistencePort;
import com.investment.domain.model.Fund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateFundService implements IUpdateFundUseCase {
    private final IFundPersistencePort fundPort;

    @Override
    public FundResponse execute(UUID fundId, UpdateFundRequest request) {
        Fund fundToUpdate = Fund.builder()
                                .id(fundId)
                                .name(request.name())
                                .minimumAmount(request.minimumAmount())
                                .category(request.category())
                                .isActive(request.isActive())
                                .build();

        Optional<Fund> fundFound = fundPort.update(fundToUpdate);

        if (fundFound.isEmpty()) throw new RuntimeException("Fund not found exception");

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
