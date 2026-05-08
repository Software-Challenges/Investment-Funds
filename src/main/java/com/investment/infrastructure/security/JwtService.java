package com.investment.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.investment.application.auth.port.output.ITokenPort;
import com.investment.domain.model.Role;
import com.investment.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.UUID;

@Component
public class JwtService implements ITokenPort {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.expiration}")
    private long expiration;

    @Override
    public String generate(User model) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                  .withIssuer(issuer)
                  .withSubject(model.getEmail())
                  .withIssuedAt(Instant.now())
                  .withExpiresAt(Instant.now().plusMillis(expiration))
                  .withJWTId(UUID.randomUUID().toString())
                  .withClaim("roles",
                             model.getRoles()
                                    .stream()
                                    .map(Role::getName)
                                    .toList()
                  )
                  .sign(algorithm);
    }

    @Override
    public String extractUsername(String accessToken) {
        DecodedJWT decoded = validate(accessToken);
        return decoded.getSubject();
    }

    private DecodedJWT validate(String accessToken) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                                  .withIssuer(issuer)
                                  .build();

        return verifier.verify(accessToken);
    }
}
