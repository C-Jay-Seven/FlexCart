package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.discount.DiscountRequest;
import com.potragis.FlexCart.dto.discount.DiscountResponse;
import com.potragis.FlexCart.service.discount.IDiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final IDiscountService discountService;

    @PostMapping
    public ResponseEntity<DiscountResponse> createDiscount(@Valid @RequestBody DiscountRequest request) {
        return ResponseEntity.ok(discountService.createDiscount(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountResponse> getDiscountById(@PathVariable Long id) {
        return ResponseEntity.ok(discountService.getDiscountById(id));
    }

    @GetMapping
    public ResponseEntity<List<DiscountResponse>> getAllDiscounts() {
        return ResponseEntity.ok(discountService.getAllDiscounts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountResponse> updateDiscount(
            @PathVariable Long id,
            @Valid @RequestBody DiscountRequest request) {
        return ResponseEntity.ok(discountService.updateDiscount(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.ok("Discount deleted successfully.");
    }
}
