package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.sales.SalesSummaryResponse;
import com.potragis.FlexCart.service.sales.ISalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sales")
public class SalesController {

    private final ISalesService salesService;

    @RequestMapping("/seller/{clientId}/summary")
    public ResponseEntity<SalesSummaryResponse> getSellerSalesSummary(@PathVariable Long clientId) {

        return ResponseEntity.ok(salesService.getSellerSalesSummary(clientId));

    }

}
