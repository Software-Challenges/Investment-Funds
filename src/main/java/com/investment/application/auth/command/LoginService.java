package com.investment.application.auth.command;

import com.investment.application.auth.dto.request.LoginRequest;
import com.investment.application.auth.dto.response.LoginResponse;
import com.investment.application.auth.port.input.ILoginUseCase;
import com.investment.application.auth.port.output.ITokenPort;
import com.investment.application.user.port.output.IUserPersistencePort;
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
