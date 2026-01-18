package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.review.ReviewRequest;
import com.potragis.FlexCart.dto.review.ReviewResponse;
import com.potragis.FlexCart.service.review.IReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<ReviewResponse> addReview(@Valid @PathVariable Long productId,
                                                    @RequestBody ReviewRequest reviewRequest) {
        ReviewResponse saved = reviewService.createReview(productId,reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{productId}/reviews")
    public List<ReviewResponse> fetchReviews(@PathVariable Long productId) {
        return reviewService.getAllReviewsByProductId(productId);
    }

    @PutMapping("/{productId}/review/{clientId}")
    public ReviewResponse updateReview(@Valid @PathVariable Long productId,
                                       @PathVariable Long clientId,
                                       @RequestBody ReviewRequest reviewRequest) {
         return reviewService.updateReview(productId, clientId, reviewRequest);
    }

    @DeleteMapping("/{productId}/review/{clientId}/{reviewId}")
    public ResponseEntity<String> removeReview(@Valid @PathVariable Long productId,
                                               @PathVariable Long clientId,
                                               @PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body("Review deleted successfully");
    }

}
