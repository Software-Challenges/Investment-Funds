package com.investment.application.auth.port.input;

import com.investment.application.auth.dto.request.LoginRequest;
import com.investment.application.auth.dto.response.LoginResponse;

public interface ILoginUseCase {
    LoginResponse execute(LoginRequest request);
}
