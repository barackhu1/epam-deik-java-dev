package com.epam.training.ticketservice.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public enum Role{
        ADMIN, USER
    }
}