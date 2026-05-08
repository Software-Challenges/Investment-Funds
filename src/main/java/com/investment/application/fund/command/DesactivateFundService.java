package com.investment.application.fund.command;

import com.investment.application.fund.port.input.IDesactivateFundUseCase;
import com.investment.application.fund.port.output.IFundPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DesactivateFundService implements IDesactivateFundUseCase {
    private final IFundPersistencePort fundPort;

    @Override
    public void execute(UUID fundId) {
        boolean isDesactivated = fundPort.desactivate(fundId);

        if (!isDesactivated) {
            throw new RuntimeException("Fund not found exception here");
        }
    }
}
