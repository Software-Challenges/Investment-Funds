package com.investment.application.user.port.output;

import com.investment.domain.model.User;
import java.util.Optional;

public interface IUserPersistencePort {
    Optional<User> findByEmail(String email);
}
