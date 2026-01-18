package com.potragis.FlexCart.repository;

import com.potragis.FlexCart.model.entity.Refunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRefundRepository extends JpaRepository<Refunds, Long> {
    List<Refunds> findByOrderId(Long orderId);
}
