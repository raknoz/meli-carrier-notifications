package com.project.meli.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.meli.demo.dtos.PackageRequestDTO;
import com.project.meli.demo.dtos.PackageResponseDTO;
import com.project.meli.demo.dtos.StateOrderRequestDTO;

import java.util.Collections;

public class TestUtils {

    public static final String ORDER_ID = "28123B";
    public static final String ORDER_STATUS_DELIVERED = "delivered";
    public static final String ORDER_SUB_STATUS_NULL = null;
    public static final String ORDER_SUB_STATUS_MSG = "En Camino";

    public static String packageRequestDtoAsJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(buildPackageRequestDto());
    }

    public static String packageResponseDtoAsJson() throws JsonProcessingException {
            return new ObjectMapper().writeValueAsString(buildPackageResponseDto());
    }

    private static PackageRequestDTO buildPackageRequestDto() {
        final PackageRequestDTO dto = new PackageRequestDTO();
        dto.setId(ORDER_ID);
        dto.setInputs(Collections.singletonList(buildStateOrderRequestDto(ORDER_STATUS_DELIVERED, ORDER_SUB_STATUS_NULL)));
        return dto;
    }

    private static StateOrderRequestDTO buildStateOrderRequestDto(final String status, final String subStatus) {
        return new StateOrderRequestDTO(status, subStatus);
    }

    private static PackageResponseDTO buildPackageResponseDto() {
        return new PackageResponseDTO(ORDER_SUB_STATUS_MSG);
    }
}
