package com.investment.infrastructure.adapter.output;

import com.investment.application.fund.dto.request.FundFilterRequest;
import com.investment.application.shared.dto.response.PagedResponse;
import com.investment.application.fund.port.output.IFundPersistencePort;
import com.investment.domain.enums.EFundCategory;
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
import java.util.concurrent.ThreadLocalRandom;

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
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Fund create(Fund fund) {
        String newCode = generateCode(fund.getCategory());
        fund.setCode(newCode);

        FundEntity newEntity = mapper.toEntity(fund);

        return mapper.toDomain(repository.save(newEntity));
    }

    private String generateCode(EFundCategory category) {
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 1000);
        return category.name() + "-" + randomNumber;
    }

    @Override
    public Optional<Fund> update(Fund fund) {
        Optional<FundEntity> entity = repository.findById(fund.getId());

        if (entity.isEmpty()) return Optional.empty();

        FundEntity entityFound = entity.get();
        entityFound.setName(fund.getName());
        entityFound.setMinimumAmount(fund.getMinimumAmount());
        entityFound.setCategory(fund.getCategory());
        entityFound.setIsActive(fund.getIsActive());

        FundEntity updatedEntity = repository.save(entityFound);

        return Optional.of(mapper.toDomain(updatedEntity));
    }

    @Override
    public boolean desactivate(UUID fundId) {
        Optional<FundEntity> entity = repository.findById(fundId);

        if (entity.isEmpty()) return false;

        FundEntity entityFound = entity.get();
        entityFound.setIsActive(false);

        repository.save(entityFound);

        return true;
    }
}
