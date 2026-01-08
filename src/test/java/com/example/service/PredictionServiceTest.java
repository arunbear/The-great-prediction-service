package com.example.service;

import com.example.dto.PredictionDto;
import com.example.entity.Match;
import com.example.entity.Prediction;
import com.example.entity.User;
import com.example.repository.MatchRepository;
import com.example.repository.PredictionRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NullAway.Init")
class PredictionServiceTest {
    @Mock
    PredictionRepository predictionRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    MatchRepository matchRepository;

    @InjectMocks
    PredictionService predictionService;

    @Test
    void uses_repositories_to_save_a_prediction() {
        // given ...
        long userId = 1L;
        long matchId = 1L;

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(new User(userId)));

        when(matchRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Match(matchId)));

        PredictionDto predictionToSave = PredictionDto.builder()
                .predictedWinner("Brentford")
                .userId(userId)
                .matchId(matchId)
                .build();
        // when ...
        predictionService.save(predictionToSave);

        // then ...
        verify(matchRepository).findById(matchId);
        verify(userRepository).findById(userId);
        verify(predictionRepository).save(any(Prediction.class));
    }

    @Test
    void uses_a_repository_to_find_a_prediction() {
        // given ...
        long predictionId = 1L;

        // when ...
        predictionService.findById(predictionId);

        verify(predictionRepository).findById(predictionId);
    }

    @Test
    void uses_a_repository_to_find_user_predictions() {
        // given ...
        long userId = 1L;

        // when ...
        predictionService.findByUser(userId);

        verify(predictionRepository).findByUser_Id(userId);
    }

    @Test
    void uses_a_repository_to_update_a_prediction() {
        // given ...
        long predictionId = 1L;
        Match match = Match.builder()
                .startTime(LocalDateTime.MAX)
                .id(1L)
                .build();
        Prediction prediction = Prediction.builder()
                .id(predictionId)
                .match(match)
                .user(new User(1L))
                .build();

        when(predictionRepository.findById(predictionId)).      thenReturn(Optional.of(prediction));
        when(predictionRepository.save(any(Prediction.class))). thenReturn(prediction);

        // when ...
        predictionService.update(predictionId, PredictionDto.builder().build());

        verify(predictionRepository).findById(predictionId);
        verify(predictionRepository).save(any(Prediction.class));
    }
}