package com.potragis.FlexCart.repository;

import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String username);

    @Query("SELECT c FROM Client c JOIN c.roles r WHERE c.id = :clientId AND r.roleName = :roleName")
    Optional<Client> findByIdAndRole(@Param("clientId") Long clientId, @Param("roleName") RoleName roleName);
}
