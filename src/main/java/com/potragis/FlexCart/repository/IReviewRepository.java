package com.potragis.FlexCart.repository;

import com.potragis.FlexCart.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByProductIdAndClientId(Long productId, Long clientId);
}
