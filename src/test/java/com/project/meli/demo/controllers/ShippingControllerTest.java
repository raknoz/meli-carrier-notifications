package com.project.meli.demo.controllers;

import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.exceptions.NotSubStatusException;
import com.project.meli.demo.services.ShippingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.project.meli.demo.utils.TestUtils.HEALTH_MSG_OK;
import static com.project.meli.demo.utils.TestUtils.ORDER_SUB_STATUS_SHIPPED_NULL_MSG;
import static com.project.meli.demo.utils.TestUtils.packageRequestDtoAsJson;
import static com.project.meli.demo.utils.TestUtils.packageResponseDtoAsJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ShippingController.class)
@AutoConfigureMockMvc
public class ShippingControllerTest {
    private static final String BASE_URL = "/package";
    private static final String URL_HEALTH = "/health";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @MockBean
    private ShippingService shippingService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @DisplayName("Class: PackageController - method: packages - flow: FAIL (Bad Request)")
    @Test
    public void packageControllerBadRequestExceptionTest() throws Exception {

        //Given
        when(shippingService.packages(any(ShippingRequestDTO.class))).thenThrow(new BadRequestException("Expected Exception test"));

        //Then
        final ResultActions resultActions =
                mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(packageRequestDtoAsJson()));

        assertNotNull(resultActions);
        assertEquals(resultActions.andReturn().getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        assertTrue(resultActions.andReturn().getResolvedException() instanceof BadRequestException);
    }

    @DisplayName("Class: PackageController - method: packages - flow: FAIL (Not Status)")
    @Test
    public void packageControllerNotStatusExceptionTest() throws Exception {

        //Given
        when(shippingService.packages(any(ShippingRequestDTO.class))).thenThrow(new NotStatusException("Expected Exception test"));

        //Then
        final ResultActions resultActions =
                mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(packageRequestDtoAsJson()));

        assertNotNull(resultActions);
        assertEquals(resultActions.andReturn().getResponse().getStatus(), HttpStatus.NOT_FOUND.value());
        assertTrue(resultActions.andReturn().getResolvedException() instanceof NotStatusException);
    }

    @DisplayName("Class: PackageController - method: packages - flow: FAIL (Not Sub Status)")
    @Test
    public void packageControllerNotSubStatusExceptionTest() throws Exception {

        //Given
        when(shippingService.packages(any(ShippingRequestDTO.class))).thenThrow(new NotSubStatusException("Expected Exception test"));

        //Then
        final ResultActions resultActions =
                mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(packageRequestDtoAsJson()));

        assertNotNull(resultActions);
        assertEquals(resultActions.andReturn().getResponse().getStatus(), HttpStatus.NOT_FOUND.value());
        assertTrue(resultActions.andReturn().getResolvedException() instanceof NotSubStatusException);
    }

    @DisplayName("Class: PackageController - method: packages - flow: Ok ")
    @Test
    public void packageControllerOkTest() throws Exception {
        //
        final String messageExpected = packageResponseDtoAsJson(ORDER_SUB_STATUS_SHIPPED_NULL_MSG);

        //Given
        when(shippingService.packages(any(ShippingRequestDTO.class))).thenReturn(ORDER_SUB_STATUS_SHIPPED_NULL_MSG);

        //Then
        final ResultActions resultActions =
                mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(packageRequestDtoAsJson()));

        assertNotNull(resultActions);
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertEquals(messageExpected, resultActions.andReturn().getResponse().getContentAsString());
    }

    @DisplayName("Class: PackageController - method: getHealth - flow: Ok ")
    @Test
    public void getHealthOkTest() throws Exception {
        //Then
        final ResultActions resultActions =
                mockMvc.perform(get(URL_HEALTH)
                        .contentType(MediaType.APPLICATION_JSON));

        assertNotNull(resultActions);
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertEquals(HEALTH_MSG_OK, resultActions.andReturn().getResponse().getContentAsString());
    }

}
