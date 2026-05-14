package com.investment.infrastructure.adapter.output.mapper;

import com.investment.domain.model.Subscription;
import com.investment.infrastructure.entity.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                UserMapper.class,
                FundMapper.class
        }
)
public interface SubscriptionMapper {
    SubscriptionEntity toEntity(Subscription domain);

    Subscription toDomain(SubscriptionEntity entity);

    List<Subscription> toDomainList(List<SubscriptionEntity> entities);
}
