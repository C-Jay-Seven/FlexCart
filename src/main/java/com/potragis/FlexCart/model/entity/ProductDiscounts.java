package com.potragis.FlexCart.model.entity;

import com.potragis.FlexCart.model.templates.TimeStamp;
import jakarta.persistence.*;

@Entity
@Table(name = "product_discounts")
public class ProductDiscounts extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discounts discount;

}

