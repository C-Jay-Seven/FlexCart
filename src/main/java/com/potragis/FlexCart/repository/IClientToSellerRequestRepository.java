package com.potragis.FlexCart.repository;

import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.entity.ClientToSellerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IClientToSellerRequestRepository extends JpaRepository<ClientToSellerRequest, Long> {
    List<ClientToSellerRequest> findByClient(Client client);
}
