package com.investment.application.port.input;

import com.investment.application.dto.request.FundFilterRequest;
import com.investment.application.dto.response.FundResponse;
import com.investment.application.dto.response.PagedResponse;

public interface IListFundsUseCase {
    PagedResponse<FundResponse> execute(int page, int size, FundFilterRequest filters);
}
