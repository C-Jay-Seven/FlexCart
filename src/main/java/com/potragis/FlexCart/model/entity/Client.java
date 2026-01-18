package com.potragis.FlexCart.model.entity;

import com.potragis.FlexCart.model.enums.RoleName;
import com.potragis.FlexCart.model.templates.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 50)
    private String phoneNumber;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Orders> orders;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> product;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles;

    @Override
    public void prePersist() { // remove @PrePersist
        super.prePersist();    // sets timestamps

        this.isActive = true;

        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }

        boolean hasClientRole = this.roles.stream()
                .anyMatch(role -> role.getRoleName() == RoleName.CLIENT);

        if (!hasClientRole) {
            Role defaultRole = new Role();
            defaultRole.setRoleName(RoleName.CLIENT);
            defaultRole.setDescription("Default role");
            defaultRole.setActive(true);
            defaultRole.setClient(this);

            this.roles.add(defaultRole);
        }
    }

}
