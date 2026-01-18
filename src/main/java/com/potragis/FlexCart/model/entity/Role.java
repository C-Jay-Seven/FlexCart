package com.potragis.FlexCart.model.entity;

import com.potragis.FlexCart.model.enums.RoleName;
import com.potragis.FlexCart.model.templates.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, length = 50)
    private RoleName roleName;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public boolean isEmpty() {
        return false;
    }
}
