package com.potragis.FlexCart.repository;

import com.potragis.FlexCart.model.entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByClientId(Long clientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.client.id = :clientId")
    void deleteByClientId(Long clientId);

    // ✅ Custom method to check if any cart exists for a client
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cart c WHERE c.client.id = :clientId")
    boolean existsByClientId(Long clientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItems c WHERE c.cart.client.id = :clientId")
    void deleteAllByClientId(Long clientId);
}
