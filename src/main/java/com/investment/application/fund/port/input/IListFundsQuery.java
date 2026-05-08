package com.investment.application.fund.port.input;

import com.investment.application.fund.dto.request.FundFilterRequest;
import com.investment.application.fund.dto.response.FundResponse;
import com.investment.application.shared.dto.response.PagedResponse;

public interface IListFundsQuery {
    PagedResponse<FundResponse> execute(int page, int size, FundFilterRequest filters);
}
