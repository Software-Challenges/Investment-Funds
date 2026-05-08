package com.investment.infrastructure.config;

import com.investment.application.auth.port.output.ITokenPort;
import com.investment.infrastructure.adapter.input.filter.TokenAuthenticationFilter;
import com.investment.infrastructure.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final ITokenPort tokenPort;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter authenticationFilter() {
        return new TokenAuthenticationFilter(tokenPort, userDetailsService);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TokenAuthenticationFilter tokenAuthenticationFilter) {
        return http.csrf(AbstractHttpConfigurer::disable)
                   .cors(Customizer.withDefaults())
                   .sessionManagement(session -> {
                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                   })
                   .authorizeHttpRequests(auth -> {
                       // Public
                       auth.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
                       auth.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll();

                       // With roles
                       auth.requestMatchers(HttpMethod.GET, "/funds").hasAnyRole("CLIENT", "ADMIN");
                       auth.requestMatchers(HttpMethod.POST, "/funds").hasAnyRole("ADMIN");
                       auth.requestMatchers(HttpMethod.GET, "/subscriptions").hasAnyRole("CLIENT", "ADMIN");

                       // All of thems
                       auth.anyRequest().authenticated();
                   })
                   .authenticationProvider(authenticationProvider())
                   .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                   .build();
    }
}
