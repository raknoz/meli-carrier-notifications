package com.project.meli.demo.services;

import com.project.meli.demo.dtos.PackageRequestDTO;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.exceptions.NotSubStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.project.meli.demo.utils.TestUtils.ORDER_SUB_STATUS_LOST_MSG;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoBlankStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoDisorderStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoEmptyStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoInOrderStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoSubStatusNotBelongStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoWrongStatus;
import static com.project.meli.demo.utils.TestUtils.buildPackageRequestDtoWrongSubStatus;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PackageServiceTest {

    @InjectMocks
    private PackageService packageService;

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Not Status Exception)")
    @Test
    public void packageServiceStatusAreEmptyTest() {
        final PackageRequestDTO requestDTO = buildPackageRequestDtoEmptyStatus();
        //When
        assertThatThrownBy(() -> {
            packageService.packages(requestDTO);
        }).isInstanceOf(NotStatusException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Status not defined")
    @Test
    public void packageServiceStatusIsBlankTest() {
        final PackageRequestDTO requestDTO = buildPackageRequestDtoBlankStatus();
        //When
        assertThatThrownBy(() -> {
            packageService.packages(requestDTO);
        }).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Status not found")
    @Test
    public void packageServiceStatusIsWrongTest() {
        final PackageRequestDTO requestDTO = buildPackageRequestDtoWrongStatus();
        //When
        assertThatThrownBy(() -> {
            packageService.packages(requestDTO);
        }).isInstanceOf(NotStatusException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Sub Status not found")
    @Test
    public void packageServiceSubStatusIsWrongTest() {
        final PackageRequestDTO requestDTO = buildPackageRequestDtoWrongSubStatus();
        //When
        assertThatThrownBy(() -> {
            packageService.packages(requestDTO);
        }).isInstanceOf(NotSubStatusException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: FAIL (Sub Status not belong to defined status")
    @Test
    public void packageServiceSubStatusNotBelongStatusTest() {
        final PackageRequestDTO requestDTO = buildPackageRequestDtoSubStatusNotBelongStatus();
        //When
        assertThatThrownBy(() -> {
            packageService.packages(requestDTO);
        }).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("Class: PackageService - method: packages - flow: OK")
    @Test
    public void packageServiceStatusDisorderTest() {
        final PackageRequestDTO requestDTO = buildPackageRequestDtoDisorderStatus();
        //When
        final String messageResponse = packageService.packages(requestDTO);
        //Then
        assertNotNull(messageResponse);
        assertEquals(ORDER_SUB_STATUS_LOST_MSG, messageResponse);
    }

    @DisplayName("Class: PackageService - method: packages - flow: OK")
    @Test
    public void packageServiceStatusInOrderTest() {
        final PackageRequestDTO requestDTO = buildPackageRequestDtoInOrderStatus();
        //When
        final String messageResponse = packageService.packages(requestDTO);
        //Then
        assertNotNull(messageResponse);
        assertEquals(ORDER_SUB_STATUS_LOST_MSG, messageResponse);
    }
}
