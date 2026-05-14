package com.investment.infrastructure.adapter.input;

import com.investment.application.subscription.dto.request.SubscribeFundRequest;
import com.investment.application.subscription.dto.request.SubscriptionFilterRequest;
import com.investment.application.shared.dto.response.PagedResponse;
import com.investment.application.subscription.dto.response.SubscriptionDetailResponse;
import com.investment.application.subscription.dto.response.SubscriptionResponse;
import com.investment.application.subscription.port.input.IGetSubscriptionByIdQuery;
import com.investment.application.subscription.port.input.IListSubscriptionsQuery;
import com.investment.application.subscription.port.input.ISubscribeFundUseCase;
import com.investment.domain.enums.ESubscriptionStatus;
import com.investment.infrastructure.adapter.input.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping(path = "subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final IGetSubscriptionByIdQuery getSubscriptionQuery;
    private final IListSubscriptionsQuery listSubscriptionsQuery;
    private final ISubscribeFundUseCase subscribeFundUseCase;

    @GetMapping(path = "/{subscriptionId}")
    public ResponseEntity<ApiResponse<SubscriptionDetailResponse>> getSubscriptionById(@PathVariable UUID subscriptionId) {
        SubscriptionDetailResponse response = getSubscriptionQuery.execute(subscriptionId);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                                           "Subscription retrieved successfully",
                                                   response));
    }

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

        PagedResponse<SubscriptionResponse> response = listSubscriptionsQuery.execute(page, size, filters);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                                          "Subscriptions retrieved successfully",
                                                   response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SubscriptionResponse>> createSubscription(@Valid @RequestBody SubscribeFundRequest request) {
        SubscriptionResponse response = subscribeFundUseCase.execute(request);

        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED.value(),
                                              "Subscription created successfully",
                                                      response),
                                    HttpStatus.CREATED);
    }
}
