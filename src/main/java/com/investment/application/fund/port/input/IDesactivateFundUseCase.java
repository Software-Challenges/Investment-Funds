package com.investment.application.fund.port.input;

import java.util.UUID;

public interface IDesactivateFundUseCase {
    void execute(UUID fundId);
}
