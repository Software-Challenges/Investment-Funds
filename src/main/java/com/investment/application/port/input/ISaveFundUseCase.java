package com.investment.application.port.input;

import com.investment.application.dto.request.SaveFundRequest;
import com.investment.application.dto.response.FundResponse;

public interface ISaveFundUseCase {
    FundResponse execute(SaveFundRequest request);
}
