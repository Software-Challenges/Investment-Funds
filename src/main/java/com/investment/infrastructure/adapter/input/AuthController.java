package com.investment.infrastructure.adapter.input;

import com.investment.application.auth.dto.request.LoginRequest;
import com.investment.application.auth.dto.response.LoginResponse;
import com.investment.application.auth.port.input.ILoginUseCase;
import com.investment.infrastructure.adapter.input.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                                          "Login successful",
                                                   response));
    }
}
