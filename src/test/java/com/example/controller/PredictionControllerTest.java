package com.example.controller;

import com.example.dto.PredictionDto;
import com.example.dto.PredictionResponse;
import com.example.dto.Status;
import com.example.entity.Match;
import com.example.entity.Prediction;
import com.example.entity.User;
import com.example.service.PredictionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PredictionController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NullAway.Init")
class PredictionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private PredictionService predictionService;

    @Test
    void uses_predictionService_to_create_a_prediction() throws Exception {
        // given ...
        var prediction = Prediction.builder()
                .id(1L)
                .predictedWinner("ABC")
                .user(new User(1L))
                .match(new Match(1L))
                .build();

        when(predictionService.save(any(PredictionDto.class)))
            .thenReturn(prediction);

        // when ...
        mockMvc.perform(
            post("/prediction")
                .content(objectMapper.writeValueAsString(prediction.toDto()))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.predictionId").value(prediction.getId()))
            .andExpect(jsonPath("$.predictedWinner").value(prediction.getPredictedWinner()))
        ;

    }

    @Test
    void uses_predictionService_to_find_a_prediction() throws Exception {
        // given ...
        long predictionId = 1;

        // when ...
        mockMvc.perform( get("/prediction/%s".formatted(predictionId)) );

        verify(predictionService).findById(predictionId);

    }

    @Test
    void uses_predictionService_to_update_a_prediction() throws Exception {
        // given ...
        long predictionId = 1;
        var predictionPatch = new JSONObject().put("predictedWinner", "Chelsea");

        when(predictionService.update(anyLong(), any(PredictionDto.class)))
                .thenReturn(PredictionResponse.builder()
                        .status(Status.SUCCESS).build());

        // when ...
        mockMvc.perform(
                patch("/prediction/%s".formatted(predictionId))
                    .content(predictionPatch.toString())
                    .contentType(MediaType.APPLICATION_JSON))
        ;

        verify(predictionService).update(anyLong(), any(PredictionDto.class));
    }

    @Test
    void uses_predictionService_to_find_user_predictions() throws Exception {
        // given ...
        long userId = 1;

        // when ...
        mockMvc.perform( get("/user/%s/predictions".formatted(userId)) );

        verify(predictionService).findByUser(userId);

    }
}