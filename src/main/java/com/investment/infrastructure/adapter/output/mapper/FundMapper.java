package com.investment.infrastructure.adapter.output.mapper;

import com.investment.domain.model.Fund;
import com.investment.infrastructure.entity.FundEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FundMapper {
    Fund toDomain(FundEntity entity);
    FundEntity toEntity(Fund domain);

    List<Fund> toDomainList(List<FundEntity> entities);
}
