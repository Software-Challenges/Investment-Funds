package com.investment.application.fund.port.input;

import com.investment.application.fund.dto.request.UpdateFundRequest;
import com.investment.application.fund.dto.response.FundResponse;
import java.util.UUID;

public interface IUpdateFundUseCase {
    FundResponse execute(UUID fundId, UpdateFundRequest request);
}
