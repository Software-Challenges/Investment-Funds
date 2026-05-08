package com.investment.infrastructure.adapter.input.dto;

public record ApiResponse<T>(int status, String message, T data) { }
