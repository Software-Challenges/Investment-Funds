package com.investment.infrastructure.adapter.output;

import com.investment.application.user.port.output.IUserPersistencePort;
import com.investment.domain.model.User;
import com.investment.infrastructure.adapter.output.mapper.UserMapper;
import com.investment.infrastructure.adapter.output.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements IUserPersistencePort {
    private final IUserRepository repository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDomain);
    }
}
