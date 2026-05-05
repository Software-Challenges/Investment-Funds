package com.investment.application.usecase;

import com.investment.application.dto.response.FundResponse;
import com.investment.application.dto.response.PagedResponse;
import com.investment.application.port.input.IListFundsUseCase;
import com.investment.application.port.output.IFundPersistencePort;
import com.investment.domain.model.Fund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListFundsService implements IListFundsUseCase {
    private final IFundPersistencePort fundPort;

    @Override
    public PagedResponse<FundResponse> execute(int page, int size, Boolean isActive) {
        PagedResponse<Fund> response = fundPort.findAll(page, size, isActive);

        List<FundResponse> fundResponseList = response.content().stream()
                                                                .map(fund -> new FundResponse(fund.getId(),
                                                                        fund.getCode(),
                                                                        fund.getName(),
                                                                        fund.getMinimumAmount(),
                                                                        fund.getCategory(),
                                                                        fund.getIsActive(),
                                                                        fund.getCreatedAt()))
                                                                .toList();

        return PagedResponse.<FundResponse>builder()
                            .content(fundResponseList)
                            .page(response.page())
                            .size(response.size())
                            .totalElements(response.totalElements())
                            .totalPages(response.totalPages())
                            .last(response.last())
                            .build();
    }
}
