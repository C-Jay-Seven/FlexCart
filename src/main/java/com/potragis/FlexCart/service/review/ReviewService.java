package com.potragis.FlexCart.service.review;

import com.potragis.FlexCart.dto.review.ReviewRequest;
import com.potragis.FlexCart.dto.review.ReviewResponse;
import com.potragis.FlexCart.mapper.review.ReviewDTOMapper;
import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.entity.Product;
import com.potragis.FlexCart.model.entity.Review;
import com.potragis.FlexCart.repository.IClientRepository;
import com.potragis.FlexCart.repository.IProductRepository;
import com.potragis.FlexCart.repository.IReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService{

    ReviewDTOMapper mapper;
    IReviewRepository reviewRepository;
    IClientRepository clientRepository;
    IProductRepository productRepository;

    @Override
    public ReviewResponse createReview(Long productId, ReviewRequest reviewRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product no found"));

        Client client = clientRepository.findById(reviewRequest.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Review review = new Review();
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        review.setProduct(product);
        review.setClient(client);
        review.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        return mapper.toDTO(review);
    }

    @Override
    public List<ReviewResponse> getAllReviewsByProductId(Long productId) {
        return reviewRepository.findAll().stream()
                .filter(review -> review.getProduct().getId().equals(productId))
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public ReviewResponse updateReview(Long productId, Long clientId, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findByProductIdAndClientId(productId, clientId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        return mapper.toDTO(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long reviewId) {
        if(reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new RuntimeException("Review not found");
        }
    }

}
