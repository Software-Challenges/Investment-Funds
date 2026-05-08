package com.investment.application.fund.port.output;

import com.investment.application.fund.dto.request.FundFilterRequest;
import com.investment.application.shared.dto.response.PagedResponse;
import com.investment.domain.model.Fund;
import java.util.Optional;
import java.util.UUID;

public interface IFundPersistencePort {
    Optional<Fund> findById(UUID fundId);

    PagedResponse<Fund> findAll(int page, int size, FundFilterRequest filters);

    boolean existsByName(String name);

    Fund create(Fund fund);

    Optional<Fund> update(Fund fund);

    boolean desactivate(UUID fundId);
}
