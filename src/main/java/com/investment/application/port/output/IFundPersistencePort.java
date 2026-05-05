package com.investment.application.port.output;

import com.investment.application.dto.request.FundFilterRequest;
import com.investment.application.dto.response.PagedResponse;
import com.investment.domain.model.Fund;
import java.util.Optional;
import java.util.UUID;

public interface IFundPersistencePort {
    Optional<Fund> findById(UUID fundId);

    PagedResponse<Fund> findAll(int page, int size, FundFilterRequest filters);

    boolean existsByCode(String code);

    boolean existsByName(String name);

    Fund save(Fund fund);

    Optional<Fund> update(Fund fund);

    void delete(UUID fundId);
}
