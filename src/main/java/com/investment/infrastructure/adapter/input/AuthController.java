package com.investment.infrastructure.adapter.input;

import com.investment.application.dto.request.LoginRequest;
import com.investment.application.dto.response.ApiResponse;
import com.investment.application.dto.response.LoginResponse;
import com.investment.application.port.input.ILoginUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthController {
    private final ILoginUseCase loginUseCase;

    @PostMapping(path = "login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = loginUseCase.execute(request);

        return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", response));
    }
}
