package com.investment.domain.exception;

public class FundAlreadyExistsException extends BusinessException {
    public FundAlreadyExistsException(String message) {
        super(message);
    }
}
