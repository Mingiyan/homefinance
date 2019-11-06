package ru.geekfactory.homefinance.dao.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
