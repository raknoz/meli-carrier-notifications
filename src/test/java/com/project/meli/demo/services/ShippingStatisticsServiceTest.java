package com.project.meli.demo.services;

import com.project.meli.demo.dtos.ShippingStatisticsResponseDTO;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.repositories.ShippingStatisticRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.project.meli.demo.utils.TestUtils.ERROR_REQUESTS;
import static com.project.meli.demo.utils.TestUtils.PARAM_DATE_FROM;
import static com.project.meli.demo.utils.TestUtils.PARAM_DATE_TO;
import static com.project.meli.demo.utils.TestUtils.TOTAL_REQUESTS;
import static com.project.meli.demo.utils.TestUtils.buildShippingStatisticEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShippingStatisticsServiceTest {
    @InjectMocks
    private ShippingStatisticsService shippingStatisticsService;
    @Mock
    private ShippingStatisticRepository shippingStatisticRepository;

    @DisplayName("Class: ShippingHistoricalService - method: getStatisticsByDate - flow: Fail (Date from is after than date to!)")
    @Test
    public void getStatisticsByDateFailDateTest() {
        //When
        assertThatThrownBy(() -> {
            shippingStatisticsService.getStatisticsByDate(PARAM_DATE_TO, PARAM_DATE_FROM);
        }).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("Class: ShippingHistoricalService - method: getStatisticsByDate - flow: Ok")
    @Test
    public void getStatisticsByDateOkTest() {
        //Given
        when(shippingStatisticRepository.getStatisticsByDate(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(buildShippingStatisticEntity());
        //When
        final ShippingStatisticsResponseDTO response = shippingStatisticsService.getStatisticsByDate(PARAM_DATE_FROM, PARAM_DATE_TO);
        //Then
        assertNotNull(response);
        assertEquals(TOTAL_REQUESTS, response.getTotalRequests());
        assertEquals(ERROR_REQUESTS, response.getErrorRequests());
    }

    @DisplayName("Class: ShippingHistoricalService - method: getStatisticsByDate - flow: Ok")
    @Test
    public void getStatisticsDateToNullOkTest() {
        //Given
        when(shippingStatisticRepository.getStatisticsByDate(any(LocalDate.class), any()))
                .thenReturn(buildShippingStatisticEntity());
        //When
        final ShippingStatisticsResponseDTO response = shippingStatisticsService.getStatisticsByDate(PARAM_DATE_FROM, null);
        //Then
        assertNotNull(response);
        assertEquals(TOTAL_REQUESTS, response.getTotalRequests());
        assertEquals(ERROR_REQUESTS, response.getErrorRequests());
    }
}
