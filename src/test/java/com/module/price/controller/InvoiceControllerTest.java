package com.module.price.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.module.price.dto.request.InvoiceDto;
import com.module.price.service.impl.DiscountCalculationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class InvoiceControllerTest {

    @InjectMocks
    private InvoiceController invoiceController;

    @Mock
    private DiscountCalculationServiceImpl discountCalculationServiceImpl;

    private MockMvc mockMvc;

    // Create an instance of ObjectMapper directly
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(invoiceController).build();
        objectMapper = new ObjectMapper(); // Instantiate ObjectMapper here
    }

    @Test
    public void testCalculate() throws Exception {
        // Arrange
        InvoiceDto invoiceDto = new InvoiceDto();

        double expectedPayableAmount = 80.0; // Expected value based on your logic

        // Mock the service call
        when(discountCalculationServiceImpl.calculatePayableAmount(any(InvoiceDto.class))).thenReturn(expectedPayableAmount);

        // Act and Assert
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoiceDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedPayableAmount));
    }
}
