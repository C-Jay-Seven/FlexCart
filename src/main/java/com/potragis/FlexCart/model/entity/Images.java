package com.potragis.FlexCart.model.entity;

import com.potragis.FlexCart.model.templates.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Images extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String filename;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "url")
    private String url;

    @Column(name = "is_primary")
    private boolean isPrimary;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
