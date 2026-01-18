package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.refund.RefundRequest;
import com.potragis.FlexCart.dto.refund.RefundResponse;
import com.potragis.FlexCart.service.refund.IRefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final IRefundService refundService;

    @PostMapping
    public ResponseEntity<RefundResponse> createRefund(@RequestBody RefundRequest request) {
        return ResponseEntity.ok(refundService.createRefund(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundResponse> getRefundById(@PathVariable Long id) {
        return ResponseEntity.ok(refundService.getRefundById(id));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<RefundResponse>> getRefundsByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(refundService.getRefundsByOrderId(orderId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefundResponse> updateRefund(
            @PathVariable Long id,
            @RequestBody RefundRequest request) {
        return ResponseEntity.ok(refundService.updateRefund(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRefund(@PathVariable Long id) {
        refundService.deleteRefund(id);
        return ResponseEntity.ok("Refund deleted successfully.");
    }
}
