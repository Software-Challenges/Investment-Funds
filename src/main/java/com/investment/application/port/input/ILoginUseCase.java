package com.investment.application.port.input;

import com.investment.application.dto.request.LoginRequest;
import com.investment.application.dto.response.LoginResponse;

public interface ILoginUseCase {
    LoginResponse execute(LoginRequest request);
}
