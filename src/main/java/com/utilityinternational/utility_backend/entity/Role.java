package com.utilityinternational.utility_backend.entity;

import jakarta.persistence.*;
import lombok.*;
 
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false, unique = true)
    private ERole name;
 
    public enum ERole {
        ROLE_CUSTOMER,
        ROLE_CALL_CENTER_AGENT
    }
}