package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("NullAway.Init") // JPA requires no-args constructor; id is set by persistence provider
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "user_") // user is reserved syntax in H2
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

}