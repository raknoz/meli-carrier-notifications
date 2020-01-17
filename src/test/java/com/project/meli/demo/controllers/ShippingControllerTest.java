package com.project.meli.demo.controllers;

import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.exceptions.NotSubStatusException;
import com.project.meli.demo.services.ShippingStatusService;
import com.project.meli.demo.services.ShippingStatisticsService;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.project.meli.demo.utils.TestUtils.HEALTH_MSG_OK;
import static com.project.meli.demo.utils.TestUtils.PARAM_DATE_FROM;
import static com.project.meli.demo.utils.TestUtils.PARAM_DATE_TO;
import static com.project.meli.demo.utils.TestUtils.SHIPPING_SUB_STATUS_SHIPPED_NULL_MSG;
import static com.project.meli.demo.utils.TestUtils.buildShippingStatisticsResponseDTO;
import static com.project.meli.demo.utils.TestUtils.packageRequestDtoAsJson;
import static com.project.meli.demo.utils.TestUtils.packageRequestDtoFailAsJson;
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
    private static final String URL_STATISTICS = "/statistics";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShippingStatusService shippingStatusService;
    @MockBean
    private ShippingStatisticsService shippingStatisticsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("Class: ShippingController - method: packages - flow: FAIL (Bad Request)")
    @Test
    public void packageControllerBadRequestExceptionTest() throws Exception {
        //Given
        when(shippingStatusService.packages(any(ShippingRequestDTO.class))).thenThrow(new BadRequestException("Expected Exception test"));
        //Then
        final ResultActions resultActions =
                mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(packageRequestDtoAsJson()));

        assertNotNull(resultActions);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resultActions.andReturn().getResponse().getStatus());
        assertTrue(resultActions.andReturn().getResolvedException() instanceof BadRequestException);
    }

    @DisplayName("Class: ShippingController - method: packages - flow: FAIL (Not Status)")
    @Test
    public void shippingControllerNotStatusExceptionTest() throws Exception {

        //Given
        when(shippingStatusService.packages(any(ShippingRequestDTO.class))).thenThrow(new NotStatusException("Expected Exception test"));

        //Then
        final ResultActions resultActions =
                mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(packageRequestDtoAsJson()));

        assertNotNull(resultActions);
        assertEquals(HttpStatus.NOT_FOUND.value(), resultActions.andReturn().getResponse().getStatus());
        assertTrue(resultActions.andReturn().getResolvedException() instanceof NotStatusException);
    }

    @DisplayName("Class: ShippingController - method: packages - flow: FAIL (Not Sub Status)")
    @Test
    public void shippingControllerNotSubStatusExceptionTest() throws Exception {

        //Given
        when(shippingStatusService.packages(any(ShippingRequestDTO.class))).thenThrow(new NotSubStatusException("Expected Exception test"));

        //Then
        final ResultActions resultActions =
                mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(packageRequestDtoAsJson()));

        assertNotNull(resultActions);
        assertEquals(HttpStatus.NOT_FOUND.value(), resultActions.andReturn().getResponse().getStatus());
        assertTrue(resultActions.andReturn().getResolvedException() instanceof NotSubStatusException);
    }

    @DisplayName("Class: ShippingController - method: packages - flow: Ok ")
    @Test
    public void shippingControllerOkTest() throws Exception {
        //
        final String messageExpected = packageResponseDtoAsJson(SHIPPING_SUB_STATUS_SHIPPED_NULL_MSG);

        //Given
        when(shippingStatusService.packages(any(ShippingRequestDTO.class))).thenReturn(SHIPPING_SUB_STATUS_SHIPPED_NULL_MSG);

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

    @DisplayName("Class: ShippingController - method: getHealth - flow: Ok ")
    @Test
    public void getHealthOkTest() throws Exception {
        //Then
        final ResultActions resultActions =
                mockMvc.perform(get(URL_HEALTH)
                        .contentType(MediaType.APPLICATION_JSON));
        //Then
        assertNotNull(resultActions);
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertEquals(HEALTH_MSG_OK, resultActions.andReturn().getResponse().getContentAsString());
    }


    @DisplayName("Class: ShippingController - method: getStatisticsByDate - flow: Ok ")
    @Test
    public void getStatisticsByDateOkTest() throws Exception {
        //
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("date_from", PARAM_DATE_FROM);
        params.add("date_to", PARAM_DATE_TO);
        //Given
        when(shippingStatisticsService.getStatisticsByDate(PARAM_DATE_FROM, PARAM_DATE_TO))
                .thenReturn(buildShippingStatisticsResponseDTO());
        //When
        final ResultActions resultActions =
                mockMvc.perform(get(URL_STATISTICS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params));
        //Then
        assertNotNull(resultActions);
        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @DisplayName("Class: ControllerHandle - method: packages - flow: FAIL (Invalid request)")
    @Test
    public void controllerHandleMethodArgumentNotValidExceptionTest() throws Exception {
        //When
        final ResultActions resultActions =
                mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(packageRequestDtoFailAsJson()));
        //Then
        assertNotNull(resultActions);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resultActions.andReturn().getResponse().getStatus());
        assertTrue(resultActions.andReturn().getResolvedException() instanceof MethodArgumentNotValidException);
    }

    @DisplayName("Class: ControllerHandle - method: packages - flow: FAIL (Server error)")
    @Test
    public void controllerHandleServerErrorTest() throws Exception {
        //
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("date_from", PARAM_DATE_FROM);
        params.add("date_to", PARAM_DATE_TO);

        //Given
        when(shippingStatisticsService.getStatisticsByDate(PARAM_DATE_FROM, PARAM_DATE_TO))
                .thenThrow(new RuntimeException("Expected Error!"));
        //Then
        final ResultActions resultActions =
                mockMvc.perform(get(URL_STATISTICS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(params));
        //Then
        assertNotNull(resultActions);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultActions.andReturn().getResponse().getStatus());
        assertTrue(resultActions.andReturn().getResolvedException() instanceof RuntimeException);
    }
}
