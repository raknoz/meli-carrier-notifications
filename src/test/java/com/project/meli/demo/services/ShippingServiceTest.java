package com.project.meli.demo.services;

import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.exceptions.NotSubStatusException;
import com.project.meli.demo.repositories.StatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.project.meli.demo.utils.TestUtils.SHIPPING_SUB_STATUS_LOST_MSG;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoBlankStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoDisorderStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoEmptyStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoInOrderStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoSubStatusNotBelongStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoWrongStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoWrongStatusAndSubStatusNull;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoWrongSubStatus;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ShippingServiceTest {
    @InjectMocks
    private ShippingService shippingService;
    @Mock
    private ShippingHistoricalService shippingHistoricalService;
    @Spy
    private StatusRepository statusRepository;

    @BeforeEach
    public void setUp() {
        statusRepository = new StatusRepository();
    }

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Not Status Exception)")
    @Test
    public void packageServiceStatusAreEmptyTest() {
        final ShippingRequestDTO requestDTO = buildPackageRequestDtoEmptyStatus();
        //When
        assertThatThrownBy(() -> {
            shippingService.packages(requestDTO);
        }).isInstanceOf(NotStatusException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Status not defined")
    @Test
    public void packageServiceStatusIsBlankTest() {
        final ShippingRequestDTO requestDTO = buildPackageRequestDtoBlankStatus();
        //When
        assertThatThrownBy(() -> {
            shippingService.packages(requestDTO);
        }).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Status not found")
    @Test
    public void packageServiceStatusIsWrongTest() {
        final ShippingRequestDTO requestDTO = buildPackageRequestDtoWrongStatus();
        //When
        assertThatThrownBy(() -> {
            shippingService.packages(requestDTO);
        }).isInstanceOf(NotStatusException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Sub Status not found")
    @Test
    public void packageServiceSubStatusIsWrongTest() {
        final ShippingRequestDTO requestDTO = buildPackageRequestDtoWrongSubStatus();
        //When
        assertThatThrownBy(() -> {
            shippingService.packages(requestDTO);
        }).isInstanceOf(NotSubStatusException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Sub Status not belong to defined status")
    @Test
    public void packageServiceSubStatusNotBelongStatusTest() {
        final ShippingRequestDTO requestDTO = buildPackageRequestDtoSubStatusNotBelongStatus();
        //When
        assertThatThrownBy(() -> {
            shippingService.packages(requestDTO);
        }).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Status not contain a null sub-status")
    @Test
    public void packageServiceStatusNotContainsSubStatusNullTest() {
        final ShippingRequestDTO requestDTO = buildPackageRequestDtoWrongStatusAndSubStatusNull();
        //When
        assertThatThrownBy(() -> {
            shippingService.packages(requestDTO);
        }).isInstanceOf(NotSubStatusException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: OK")
    @Test
    public void packageServiceStatusDisorderTest() {
        final ShippingRequestDTO requestDTO = buildPackageRequestDtoDisorderStatus();
        //When
        final String messageResponse = shippingService.packages(requestDTO);
        //Then
        assertNotNull(messageResponse);
        assertEquals(SHIPPING_SUB_STATUS_LOST_MSG, messageResponse);
    }

    @DisplayName("Class: PackageService - method: packages - flow: OK")
    @Test
    public void packageServiceStatusInOrderTest() {
        final ShippingRequestDTO requestDTO = buildPackageRequestDtoInOrderStatus();
        //When
        final String messageResponse = shippingService.packages(requestDTO);
        //Then
        assertNotNull(messageResponse);
        assertEquals(SHIPPING_SUB_STATUS_LOST_MSG, messageResponse);
    }
}
