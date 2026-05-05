package com.investment.infrastructure.adapter.output;

import com.investment.application.dto.request.SubscriptionFilterRequest;
import com.investment.application.dto.response.PagedResponse;
import com.investment.application.port.output.ISubscriptionPersistencePort;
import com.investment.domain.model.Subscription;
import com.investment.infrastructure.adapter.output.mapper.SubscriptionMapper;
import com.investment.infrastructure.adapter.output.repository.ISubscriptionRepository;
import com.investment.infrastructure.adapter.output.specification.SubscriptionSpecification;
import com.investment.infrastructure.entity.SubscriptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionPersistenceAdapter implements ISubscriptionPersistencePort {
    private final ISubscriptionRepository repository;
    private final SubscriptionMapper mapper;

    @Override
    public PagedResponse<Subscription> findAll(int page, int size, SubscriptionFilterRequest filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "subscribedAt"));
        Page<SubscriptionEntity> entityPage = (filters == null && !filters.hasFilter())
                                                ? repository.findAll(pageable)
                                                : repository.findAll(SubscriptionSpecification.withFilters(filters), pageable);

        List<Subscription> subscriptions = mapper.toDomainList(entityPage.getContent());

        return PagedResponse.<Subscription>builder()
                            .content(subscriptions)
                            .page(entityPage.getNumber())
                            .size(entityPage.getSize())
                            .totalElements(entityPage.getTotalElements())
                            .totalPages(entityPage.getTotalPages())
                            .last(entityPage.isLast())
                            .build();
    }
}
