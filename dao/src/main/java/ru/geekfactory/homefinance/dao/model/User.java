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

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
