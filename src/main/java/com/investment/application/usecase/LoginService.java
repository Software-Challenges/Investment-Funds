package com.investment.application.usecase;

import com.investment.application.dto.request.LoginRequest;
import com.investment.application.dto.response.LoginResponse;
import com.investment.application.port.input.ILoginUseCase;
import com.investment.application.port.output.ITokenPort;
import com.investment.application.port.output.IUserPersistencePort;
import com.investment.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements ILoginUseCase {
    private final IUserPersistencePort userPort;
    private final ITokenPort tokenPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse execute(LoginRequest request) {
        User user = userPort.findByEmail(request.email())
                            .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String accessToken = tokenPort.generate(user);

        return new LoginResponse(user.getId(), accessToken);
    }
}
