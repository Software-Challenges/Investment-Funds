package com.investment.application.fund.port.input;

import com.investment.application.fund.dto.response.FundResponse;
import java.util.UUID;

public interface IGetFundByIdQuery {
    FundResponse execute(UUID fundId);
}
