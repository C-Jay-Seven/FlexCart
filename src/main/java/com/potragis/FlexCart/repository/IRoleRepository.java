package com.potragis.FlexCart.repository;

import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.entity.Role;
import com.potragis.FlexCart.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRoleName(RoleName roleName);

    Optional<Role> findByRoleName(RoleName roleName);

    Optional<Role> findByRoleNameAndClient(RoleName roleName, Client client);

    boolean existsByClientIdAndRoleName(Long clientId, RoleName roleName);
}