package com.investment.infrastructure.adapter.input;

import com.investment.application.dto.request.SubscriptionFilterRequest;
import com.investment.application.dto.response.ApiResponse;
import com.investment.application.dto.response.PagedResponse;
import com.investment.application.dto.response.SubscriptionResponse;
import com.investment.application.port.input.IListSubscriptionsUseCase;
import com.investment.domain.enums.ESubscriptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping(path = "subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final IListSubscriptionsUseCase useCase;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<SubscriptionResponse>>> getAllSubscriptions(@RequestParam(required = false, defaultValue = "0") int page,
                                                                                                @RequestParam(required = false, defaultValue = "25") int size,
                                                                                                @RequestParam(required = false) ESubscriptionStatus status,
                                                                                                @RequestParam(required = false) UUID userId,
                                                                                                @RequestParam(required = false) UUID fundId,
                                                                                                @RequestParam(required = false) LocalDate cancelledFrom,
                                                                                                @RequestParam(required = false) LocalDate cancelledTo) {
        SubscriptionFilterRequest filters = SubscriptionFilterRequest.builder()
                                                                     .status(status)
                                                                     .userId(userId)
                                                                     .fundId(fundId)
                                                                     .cancelledFrom(cancelledFrom)
                                                                     .cancelledTo(cancelledTo)
                                                                     .build();

        PagedResponse<SubscriptionResponse> response = useCase.execute(page, size, filters);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                                          "Subscriptions retrieved successfully",
                                                   response));
    }
}
