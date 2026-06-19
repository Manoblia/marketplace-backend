package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;
    
    @Enumerated(EnumType.STRING)
    private RoleName rolName;
}
