package com.app.quantitymeasurement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QuantityMeasurementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void givenValidInput_whenCompareEndpointCalled_thenReturn200() throws Exception {
        String json = """
                {
                  "thisQuantityDTO": {"value": 1.0, "unit": "FEET", "measurementType": "LengthUnit"},
                  "thatQuantityDTO": {"value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit"}
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("COMPARE"))
                .andExpect(jsonPath("$.resultString").value("true"));
    }

    @Test
    void givenValidInput_whenAddEndpointCalled_thenPersistAndReturn200() throws Exception {
        String json = """
                {
                  "thisQuantityDTO": {"value": 1.0, "unit": "FEET", "measurementType": "LengthUnit"},
                  "thatQuantityDTO": {"value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit"}
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("ADD"))
                .andExpect(jsonPath("$.resultValue").value(2.0));
    }

    @Test
    void givenPreviousOperation_whenGetHistoryByOperation_thenReturnSavedData() throws Exception {
        String json = """
                {
                  "thisQuantityDTO": {"value": 1.0, "unit": "FEET", "measurementType": "LengthUnit"},
                  "thatQuantityDTO": {"value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit"}
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/quantities/history/operation/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operation").value("COMPARE"));
    }

    @Test
    void givenPreviousOperation_whenGetCount_thenReturnCount() throws Exception {
        String json = """
                {
                  "thisQuantityDTO": {"value": 1.0, "unit": "FEET", "measurementType": "LengthUnit"},
                  "thatQuantityDTO": {"value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit"}
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/quantities/count/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.matchesPattern("\\d+")));
    }

    @Test
    void givenInvalidInput_whenCompareEndpointCalled_thenReturn400() throws Exception {
        String json = """
                {
                  "thisQuantityDTO": {"value": null, "unit": "", "measurementType": ""},
                  "thatQuantityDTO": {"value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit"}
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenDifferentMeasurementTypes_whenAddEndpointCalled_thenReturn400() throws Exception {
        String json = """
                {
                  "thisQuantityDTO": {"value": 1.0, "unit": "FEET", "measurementType": "LengthUnit"},
                  "thatQuantityDTO": {"value": 1.0, "unit": "KILOGRAM", "measurementType": "WeightUnit"}
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Quantity Measurement Error"));
    }

    @Test
    void givenDivideByZero_whenDivideEndpointCalled_thenReturn400() throws Exception {
        String json = """
                {
                  "thisQuantityDTO": {"value": 1.0, "unit": "FEET", "measurementType": "LengthUnit"},
                  "thatQuantityDTO": {"value": 0.0, "unit": "INCHES", "measurementType": "LengthUnit"}
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/divide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

}
