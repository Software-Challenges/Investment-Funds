package com.investment.infrastructure.adapter.output;

import com.investment.application.dto.request.FundFilterRequest;
import com.investment.application.dto.response.PagedResponse;
import com.investment.application.port.output.IFundPersistencePort;
import com.investment.domain.model.Fund;
import com.investment.infrastructure.adapter.output.mapper.FundMapper;
import com.investment.infrastructure.adapter.output.repository.IFundRepository;
import com.investment.infrastructure.adapter.output.specification.FundSpecification;
import com.investment.infrastructure.entity.FundEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FundPersistenceAdapter implements IFundPersistencePort {
    private final IFundRepository repository;
    private final FundMapper mapper;

    @Override
    public Optional<Fund> findById(UUID fundId) {
        return repository.findById(fundId)
                         .map(mapper::toDomain);
    }

    @Override
    public PagedResponse<Fund> findAll(int page, int size, FundFilterRequest filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<FundEntity> entityPage = (filters == null && !filters.hasFilter())
                                      ? repository.findAll(pageable)
                                      : repository.findAll(FundSpecification.withFilters(filters), pageable);

        List<Fund> funds = mapper.toDomainList(entityPage.getContent());

        return PagedResponse.<Fund>builder()
                            .content(funds)
                            .page(entityPage.getNumber())
                            .size(entityPage.getSize())
                            .totalElements(entityPage.getTotalElements())
                            .totalPages(entityPage.getTotalPages())
                            .last(entityPage.isLast())
                            .build();
    }

    @Override
    public Fund save(Fund fund) {
        FundEntity newEntity = FundEntity.builder()
                                         .code(fund.getCode())
                                         .name(fund.getName())
                                         .minimumAmount(fund.getMinimumAmount())
                                         .category(fund.getCategory())
                                         .isActive(true)
                                         .build();

        return mapper.toDomain(repository.save(newEntity));
    }

    @Override
    public Optional<Fund> update(Fund fund) {
        Optional<FundEntity> entity = repository.findById(fund.getId());

        if (entity.isEmpty()) return Optional.empty();

        FundEntity entityFound = entity.get();
        entityFound.setCode(fund.getCode());
        entityFound.setName(fund.getName());
        entityFound.setMinimumAmount(fund.getMinimumAmount());
        entityFound.setCategory(fund.getCategory());
        entityFound.setIsActive(fund.getIsActive());

        FundEntity updatedEntity = repository.save(entityFound);

        return Optional.of(mapper.toDomain(updatedEntity));
    }

    @Override
    public void delete(UUID fundId) {
        Optional<FundEntity> entity = repository.findById(fundId);

        entity.ifPresent(e -> {
            e.setIsActive(false);
            repository.save(e);
        });
    }
}
