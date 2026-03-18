package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuantityMeasurementController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IQuantityMeasurementService service;

    @Test
    void givenValidInput_whenCompare_thenReturn200() throws Exception {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(1.0, "FEET", "LengthUnit"),
                new QuantityDTO(12.0, "INCHES", "LengthUnit")
        );

        QuantityMeasurementDTO response = QuantityMeasurementDTO.builder()
                .thisValue(1.0)
                .thisUnit("FEET")
                .thisMeasurementType("LengthUnit")
                .thatValue(12.0)
                .thatUnit("INCHES")
                .thatMeasurementType("LengthUnit")
                .operation("COMPARE")
                .resultString("true")
                .error(false)
                .build();

        when(service.compare(any(QuantityInputDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("COMPARE"))
                .andExpect(jsonPath("$.resultString").value("true"))
                .andExpect(jsonPath("$.error").value(false));
    }

    @Test
    void givenValidInput_whenAdd_thenReturn200() throws Exception {
        QuantityInputDTO input = new QuantityInputDTO(
                new QuantityDTO(1.0, "FEET", "LengthUnit"),
                new QuantityDTO(12.0, "INCHES", "LengthUnit")
        );

        QuantityMeasurementDTO response = QuantityMeasurementDTO.builder()
                .thisValue(1.0)
                .thisUnit("FEET")
                .thisMeasurementType("LengthUnit")
                .thatValue(12.0)
                .thatUnit("INCHES")
                .thatMeasurementType("LengthUnit")
                .operation("ADD")
                .resultValue(2.0)
                .resultUnit("FEET")
                .resultMeasurementType("LengthUnit")
                .error(false)
                .build();

        when(service.add(any(QuantityInputDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("ADD"))
                .andExpect(jsonPath("$.resultValue").value(2.0))
                .andExpect(jsonPath("$.resultUnit").value("FEET"))
                .andExpect(jsonPath("$.error").value(false));
    }

    @Test
    void givenValidOperation_whenGetHistory_thenReturn200() throws Exception {
        QuantityMeasurementDTO dto = QuantityMeasurementDTO.builder()
                .operation("COMPARE")
                .resultString("true")
                .error(false)
                .build();

        when(service.getHistoryByOperation("COMPARE")).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/quantities/history/operation/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operation").value("COMPARE"))
                .andExpect(jsonPath("$[0].resultString").value("true"));
    }

    @Test
    void givenValidMeasurementType_whenGetHistoryByType_thenReturn200() throws Exception {
        QuantityMeasurementDTO dto = QuantityMeasurementDTO.builder()
                .thisMeasurementType("LengthUnit")
                .operation("ADD")
                .error(false)
                .build();

        when(service.getHistoryByMeasurementType("LengthUnit")).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/quantities/history/type/LengthUnit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].thisMeasurementType").value("LengthUnit"))
                .andExpect(jsonPath("$[0].operation").value("ADD"));
    }

    @Test
    void givenValidOperation_whenGetCount_thenReturn200() throws Exception {
        when(service.getOperationCount("COMPARE")).thenReturn(5L);

        mockMvc.perform(get("/api/v1/quantities/count/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void givenErrorHistoryRequest_whenGetErroredHistory_thenReturn200() throws Exception {
        QuantityMeasurementDTO dto = QuantityMeasurementDTO.builder()
                .operation("ADD")
                .error(true)
                .errorMessage("Invalid operation")
                .build();

        when(service.getErroredHistory()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/quantities/history/errored"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].error").value(true))
                .andExpect(jsonPath("$[0].errorMessage").value("Invalid operation"));
    }

    @Test
    void givenInvalidRequestBody_whenCompare_thenReturn400() throws Exception {
        String invalidJson = """
                {
                  "thisQuantityDTO": {"value": null, "unit": "", "measurementType": ""},
                  "thatQuantityDTO": {"value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit"}
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}