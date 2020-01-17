package com.project.meli.demo.controllers;

import com.project.meli.demo.entities.ShippingHistoricalEntity;
import com.project.meli.demo.entities.ShippingMovement;
import com.project.meli.demo.entities.ShippingStatus;
import com.project.meli.demo.entities.ShippingSubStatus;
import com.project.meli.demo.repositories.ShippingHistoricalRepository;
import com.project.meli.demo.services.ShippingHistoricalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.meli.demo.utils.TestUtils.HEALTH_MSG_OK;
import static com.project.meli.demo.utils.TestUtils.SHIPPING_ID;
import static com.project.meli.demo.utils.TestUtils.SHIPPING_SUB_STATUS_NULL_MSG;
import static com.project.meli.demo.utils.TestUtils.packageRequestDtoAsJson;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShippingControllerTestIT {
    private static final String BASE_URL = "/package";
    private static final String URL_HEALTH = "/health";
    private static final String URL_STATISTICS = "/statistics";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ShippingHistoricalService shippingHistoricalService;
    @Autowired
    private ShippingHistoricalRepository shippingHistoricalRepository;

    @BeforeEach
    public void setUp() {
        /* Clear the Table because in the first test it will be insert a register and fail other one.*/
        shippingHistoricalRepository.deleteAll();
    }

    @DisplayName("Class: ShippingController - method: packages - flow: OK")
    @Test
    public void getLastShippingStatusOk() throws Exception {
        //Then
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(packageRequestDtoAsJson()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("package", is(SHIPPING_SUB_STATUS_NULL_MSG)));
    }

    @DisplayName("Class: ShippingController - method: health - flow: OK")
    @Test
    public void getHealthApplicationOk() throws Exception {
        //Then
        final String result = mockMvc.perform(get(URL_HEALTH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        //Then
        assertNotNull(result);
        assertEquals(HEALTH_MSG_OK, result);
    }

    @DisplayName("Class: ShippingController - method: getStatistic - flow: OK")
    @Test
    public void getStatisticsOk() throws Exception {
        //Before
        final String expectedResult = "{\"successful_requests\":1,\"error_requests\":0,\"total_request\":1}";
        final ShippingHistoricalEntity entityHistorical = shippingHistoricalService.shippingQueryRegister(SHIPPING_ID);
        final ShippingMovement shippingMovement = new ShippingMovement(ShippingStatus.READY_TO_SHIP, ShippingSubStatus.PRINTED);
        shippingHistoricalService.shippingQueryRegister(entityHistorical, shippingMovement);
        //Then
        final String result = mockMvc.perform(get(URL_STATISTICS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("date_from", "")
                .param("date_to", ""))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        //Then
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }
}
