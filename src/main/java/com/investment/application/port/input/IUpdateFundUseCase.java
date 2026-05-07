package com.investment.application.port.input;

import com.investment.application.dto.request.UpdateFundRequest;
import com.investment.application.dto.response.FundResponse;
import java.util.UUID;

public interface IUpdateFundUseCase {
    FundResponse execute(UUID fundId, UpdateFundRequest request);
}
