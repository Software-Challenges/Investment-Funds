package com.investment.infrastructure.adapter.input;

import com.investment.application.dto.request.FundFilterRequest;
import com.investment.application.dto.response.ApiResponse;
import com.investment.application.dto.response.FundResponse;
import com.investment.application.dto.response.PagedResponse;
import com.investment.application.port.input.IListFundsUseCase;
import com.investment.domain.enums.EFundCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "funds")
@RequiredArgsConstructor
public class FundController {
    private final IListFundsUseCase useCase;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<FundResponse>>> getAllFunds(@RequestParam(required = false, defaultValue = "0") int page,
                                                                                @RequestParam(required = false, defaultValue = "25") int size,
                                                                                @RequestParam(required = false) String name,
                                                                                @RequestParam(required = false) BigDecimal minimumAmount,
                                                                                @RequestParam(required = false)EFundCategory category,
                                                                                @RequestParam(required = false) Boolean isActive) {
        FundFilterRequest filters = FundFilterRequest.builder()
                                                     .name(name)
                                                     .minimumAmount(minimumAmount)
                                                     .category(category)
                                                     .isActive(isActive)
                                                     .build();

        PagedResponse<FundResponse> response = useCase.execute(page, size, filters);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Funds retrieved successfully", response));
    }
}
