package com.investment.infrastructure.adapter.input;

import com.investment.application.fund.dto.request.FundFilterRequest;
import com.investment.application.fund.dto.request.CreateFundRequest;
import com.investment.application.fund.dto.request.UpdateFundRequest;
import com.investment.application.fund.dto.response.FundResponse;
import com.investment.application.fund.port.input.*;
import com.investment.application.shared.dto.response.PagedResponse;
import com.investment.domain.enums.EFundCategory;
import com.investment.infrastructure.adapter.input.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(path = "funds")
@RequiredArgsConstructor
public class FundController {
    private final IGetFundByIdQuery getFundQuery;
    private final IListFundsQuery listFundsQuery;
    private final ICreateFundUseCase createFundUseCase;
    private final IUpdateFundUseCase updateFundUseCase;
    private final IDesactivateFundUseCase desactivateFundUseCase;

    @GetMapping(path = "/{fundId}")
    public ResponseEntity<ApiResponse<FundResponse>> getFundById(@PathVariable UUID fundId) {
        FundResponse response = getFundQuery.execute(fundId);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                                           "Fund retrieved successfully",
                                                   response));
    }

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

        PagedResponse<FundResponse> response = listFundsQuery.execute(page, size, filters);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                                          "Funds retrieved successfully",
                                                   response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FundResponse>> saveNewFund(@Valid @RequestBody CreateFundRequest request) {
        FundResponse response = createFundUseCase.execute(request);

        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED.value(),
                                             "Fund created successfully",
                                                      response),
                                    HttpStatus.CREATED);
    }

    @PutMapping("/{fundId}")
    public ResponseEntity<ApiResponse<FundResponse>> updateFund(@PathVariable UUID fundId, @Valid @RequestBody UpdateFundRequest request) {
        FundResponse response = updateFundUseCase.execute(fundId, request);

        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(),
                                             "Fund updated successfully",
                                                      response),
                                    HttpStatus.OK);
    }

    @DeleteMapping(path = "/{fundId}")
    public ResponseEntity<ApiResponse<Void>> desactivateFund(@PathVariable UUID fundId) {
        desactivateFundUseCase.execute(fundId);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                                           "Fund deleted successfully",
                                              null));
    }
}
