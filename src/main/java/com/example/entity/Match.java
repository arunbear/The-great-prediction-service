package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@SuppressWarnings("NullAway.Init") // JPA requires no-args constructor; fields set by persistence provider
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "match") // user is reserved syntax in H2
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime startTime;

    public Match(long id) {
        this.id = id;
    }

    public boolean isOpen() {
        return getStartTime().isAfter(LocalDateTime.now());
    }
}