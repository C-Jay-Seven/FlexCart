package com.potragis.FlexCart.dto.review;

import com.potragis.FlexCart.model.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {

    private Long id;
    private int rating;
    private String comment;
    private String username;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.username = review.getClient().getUsername();
    }

}
