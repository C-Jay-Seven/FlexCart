package com.potragis.FlexCart.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    @Min(1)
    @Max(5)
    @NotBlank(message = "Rating must not be empty")
    private int rating;

    @NotBlank(message = "Comment must not be empty")
    private String comment;

    private Long clientId;

}
