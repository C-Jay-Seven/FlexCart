package com.potragis.FlexCart.repository;

import com.potragis.FlexCart.model.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImageRepository extends JpaRepository<Images, Long> {
    List<Images> findByProductId(Long productId);
}
