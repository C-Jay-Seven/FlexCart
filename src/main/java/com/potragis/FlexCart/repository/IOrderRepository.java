package com.potragis.FlexCart.repository;


import com.potragis.FlexCart.model.entity.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByClientIdOrderByCreatedAtDesc(Long clientId);

    // ✅ Seller total sales
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Orders o WHERE o.client.id = :sellerId")
    Double getSellerTotalSales(@Param("sellerId") Long sellerId);

    // ✅ Seller total orders
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.client.id = :sellerId")
    Long getSellerTotalOrders(@Param("sellerId") Long sellerId);

    // ✅ Orders per seller (via products)
    @Query("""
        SELECT DISTINCT o FROM Orders o
        JOIN o.orderItems oi
        WHERE oi.product.client.id = :sellerId
    """)
    List<Orders> findBySellerId(@Param("sellerId") Long sellerId);

}
