package com.potragis.FlexCart.service.review;

import com.potragis.FlexCart.dto.review.ReviewRequest;
import com.potragis.FlexCart.dto.review.ReviewResponse;
import com.potragis.FlexCart.model.entity.Product;

import java.util.List;

public interface IReviewService {

    ReviewResponse createReview(Long productId, ReviewRequest reviewRequest);
    List<ReviewResponse> getAllReviewsByProductId(Long productId);
    ReviewResponse updateReview(Long productId, Long clientId, ReviewRequest reviewRequest);
    void deleteReview(Long reviewId);

}
