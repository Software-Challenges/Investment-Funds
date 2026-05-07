package com.investment.infrastructure.adapter.input;

import com.investment.application.dto.request.FundFilterRequest;
import com.investment.application.dto.request.SaveFundRequest;
import com.investment.application.dto.request.UpdateFundRequest;
import com.investment.application.dto.response.ApiResponse;
import com.investment.application.dto.response.FundResponse;
import com.investment.application.dto.response.PagedResponse;
import com.investment.application.port.input.IListFundsUseCase;
import com.investment.application.port.input.ISaveFundUseCase;
import com.investment.application.port.input.IUpdateFundUseCase;
import com.investment.domain.enums.EFundCategory;
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
    private final IListFundsUseCase listFundsUseCase;
    private final ISaveFundUseCase saveFundUseCase;
    private final IUpdateFundUseCase updateFundUseCase;

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

        PagedResponse<FundResponse> response = listFundsUseCase.execute(page, size, filters);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                                          "Funds retrieved successfully",
                                                   response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FundResponse>> saveNewFund(@Valid @RequestBody SaveFundRequest request) {
        FundResponse response = saveFundUseCase.execute(request);

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
}
