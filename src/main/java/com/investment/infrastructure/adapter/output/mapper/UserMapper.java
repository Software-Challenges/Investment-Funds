package com.investment.infrastructure.adapter.output.mapper;

import com.investment.domain.model.Role;
import com.investment.domain.model.User;
import com.investment.infrastructure.entity.RoleEntity;
import com.investment.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toDomain(UserEntity entity);

    Set<Role> toRoleSet(Set<RoleEntity> roles);
}
