package com.investment.application.port.output;

import com.investment.domain.model.User;

public interface ITokenPort {
    String generate(User model);
    String extractUsername(String accessToken);
}
