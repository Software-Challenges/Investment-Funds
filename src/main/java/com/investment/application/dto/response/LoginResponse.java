package com.investment.application.dto.response;

import java.util.UUID;

public record LoginResponse(UUID userId, String accessToken) { }
