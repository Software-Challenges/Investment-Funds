package com.investment.application.fund.command;

import com.investment.application.fund.dto.request.CreateFundRequest;
import com.investment.application.fund.dto.response.FundResponse;
import com.investment.application.fund.port.input.ICreateFundUseCase;
import com.investment.application.fund.port.output.IFundPersistencePort;
import com.investment.domain.exception.FundAlreadyExistsException;
import com.investment.domain.model.Fund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFundService implements ICreateFundUseCase {
    private final IFundPersistencePort fundPort;

    @Override
    public FundResponse execute(CreateFundRequest request) {
        if (fundPort.existsByName(request.name())) {
            throw new FundAlreadyExistsException("A fund with this name already exists");
        }

        Fund newFund = Fund.builder()
                           .name(request.name())
                           .minimumAmount(request.minimumAmount())
                           .category(request.category())
                           .build();

        Fund fundSaved = fundPort.save(newFund);

        return FundResponse.builder()
                           .id(fundSaved.getId())
                           .code(fundSaved.getCode())
                           .name(fundSaved.getName())
                           .minimumAmount(fundSaved.getMinimumAmount())
                           .category(fundSaved.getCategory())
                           .isActive(fundSaved.getIsActive())
                           .createdAt(fundSaved.getCreatedAt())
                           .build();
    }
}
