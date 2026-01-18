package com.potragis.FlexCart.mapper.review;

import com.potragis.FlexCart.dto.review.ReviewRequest;
import com.potragis.FlexCart.dto.review.ReviewResponse;
import com.potragis.FlexCart.model.entity.Review;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewDTOMapper {

    public Review toEntity(ReviewRequest reviewRequest) {
        Review review = new Review();
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        return review;
    }

    public ReviewResponse toDTO(Review review) {
        return new ReviewResponse(review);
    }

    public List<ReviewResponse> toDTO(List<Review> reviews) {
        return reviews.stream()
                .map(this::toDTO)
                .toList();
    }

}
