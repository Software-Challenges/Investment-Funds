package com.investment.application.user.port.output;

import com.investment.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface IUserPersistencePort {
    Optional<User> findById(UUID userId);

    Optional<User> findByEmail(String email);

    User create(User user);

    Optional<User> update(User user);

    void updateBalance(User user);
}
