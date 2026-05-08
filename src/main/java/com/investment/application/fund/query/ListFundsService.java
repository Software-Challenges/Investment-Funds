package com.investment.application.fund.query;

import com.investment.application.fund.dto.request.FundFilterRequest;
import com.investment.application.fund.dto.response.FundResponse;
import com.investment.application.shared.dto.response.PagedResponse;
import com.investment.application.fund.port.input.IListFundsQuery;
import com.investment.application.fund.port.output.IFundPersistencePort;
import com.investment.domain.model.Fund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListFundsService implements IListFundsQuery {
    private final IFundPersistencePort fundPort;

    @Override
    public PagedResponse<FundResponse> execute(int page, int size, FundFilterRequest filters) {
        PagedResponse<Fund> response = fundPort.findAll(page, size, filters);

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
