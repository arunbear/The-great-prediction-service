package com.example.entity;

import com.example.dto.PredictionDto;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("NullAway.Init") // JPA requires no-args constructor; fields set by persistence provider
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "prediction")
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String predictedWinner;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    public PredictionDto toDto() {
        return PredictionDto.builder()
                .predictionId(id)
                .predictedWinner(predictedWinner)
                .userId(user.getId())
                .matchId(match.getId())
                .build();
    }

    public boolean isOpen() {
        return getMatch().isOpen();
    }

    public boolean isClosed() {
        return !isOpen();
    }
}
