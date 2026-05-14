package com.investment.infrastructure.adapter.output;

import com.investment.application.user.port.output.IUserPersistencePort;
import com.investment.domain.model.User;
import com.investment.infrastructure.adapter.output.mapper.UserMapper;
import com.investment.infrastructure.adapter.output.repository.IUserRepository;
import com.investment.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements IUserPersistencePort {
    private final IUserRepository repository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findById(UUID userId) {
        return repository.findById(userId)
                         .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email)
                         .map(mapper::toDomain);
    }

    @Override
    public User create(User user) {
        UserEntity newEntity = mapper.toEntity(user);

        return mapper.toDomain(repository.save(newEntity));
    }

    @Override
    public Optional<User> update(User user) {
        Optional<UserEntity> entity = repository.findById(user.getId());

        if (entity.isEmpty()) return Optional.empty();

        UserEntity entityFound = entity.get();
        entityFound.setFullName(user.getFullName());
        entityFound.setPhoneNumber(user.getPhoneNumber());
        entityFound.setEmail(user.getEmail());
        entityFound.setNotificationPreference(user.getNotificationPreference());
        entityFound.setIsActive(user.getIsActive());

        UserEntity updatedEntity = repository.save(entityFound);

        return Optional.of(mapper.toDomain(updatedEntity));
    }

    @Override
    public void updateBalance(User user) {
        repository.findById(user.getId())
                  .ifPresent(entity -> {
                      entity.setBalance(user.getBalance());
                      repository.save(entity);
                  });
    }
}
