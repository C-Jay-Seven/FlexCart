package com.potragis.FlexCart.model.entity;

import com.potragis.FlexCart.model.enums.ClientToSellerRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client_to_seller_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientToSellerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private String shopName;

    @Enumerated(EnumType.STRING)
    private ClientToSellerRequestStatus status;
}

