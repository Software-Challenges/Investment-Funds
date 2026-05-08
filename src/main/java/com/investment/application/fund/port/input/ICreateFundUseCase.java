package com.investment.application.fund.port.input;

import com.investment.application.fund.dto.request.CreateFundRequest;
import com.investment.application.fund.dto.response.FundResponse;

public interface ICreateFundUseCase {
    FundResponse execute(CreateFundRequest request);
}
