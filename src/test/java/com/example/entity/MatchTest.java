package com.example.entity;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MatchTest {

    @Test
    void a_match_is_open_if_its_start_time_is_in_the_future() {
        // Given
        LocalDateTime futureTime = LocalDateTime.now().plusHours(1);
        Match match = new Match(1L, futureTime);

        // When & Then
        then(match.isOpen()).isTrue();
    }

    @Test
    void a_match_is_closed_if_its_start_time_is_in_the_past() {
        // Given
        LocalDateTime pastTime = LocalDateTime.now().minusHours(1);
        Match match = new Match(1L, pastTime);

        // When & Then
        then(match.isOpen()).isFalse();
    }

    @Test
    void a_match_is_closed_if_its_start_time_is_now() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Match match = new Match(1L, now);

        // When & Then
        then(match.isOpen()).isFalse();
    }
}
