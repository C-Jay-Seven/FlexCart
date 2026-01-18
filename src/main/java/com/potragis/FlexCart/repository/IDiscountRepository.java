package com.potragis.FlexCart.repository;

import com.potragis.FlexCart.model.entity.Discounts;
import com.potragis.FlexCart.model.enums.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDiscountRepository extends JpaRepository<Discounts, Long> {
    Optional<Discounts> findByDiscountType(DiscountType discountType);
}
