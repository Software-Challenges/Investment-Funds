package com.investment.application.auth.dto.response;

import java.util.UUID;

public record LoginResponse(UUID userId, String accessToken) { }
