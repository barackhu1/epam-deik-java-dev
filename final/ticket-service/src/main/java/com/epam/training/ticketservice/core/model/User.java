package com.epam.training.ticketservice.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public enum Role {
        ADMIN, USER
    }
}
