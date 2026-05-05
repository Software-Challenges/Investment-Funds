package com.investment.infrastructure.security;

import com.investment.application.port.output.IUserPersistencePort;
import com.investment.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserPersistencePort userPort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userPort.findByEmail(email)
                            .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        List<SimpleGrantedAuthority> roles = user.getRoles()
                                                 .stream()
                                                 .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                                                 .toList();

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                                                                      user.getPassword(),
                                                                      user.getIsActive(),
                                                      true,
                                                    true,
                                                      true,
                                                                      roles);
    }
}
