package com.project.meli.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.meli.demo.dtos.PackageRequestDTO;
import com.project.meli.demo.dtos.PackageResponseDTO;
import com.project.meli.demo.dtos.StateOrderRequestDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtils {

    public static final String VALUE_EMPTY = "";
    public static final String ORDER_ID = "28123B";
    public static final String ORDER_STATUS_DELIVERED = "delivered";
    public static final String ORDER_STATUS_NOT_DELIVERED = "not_delivered";
    public static final String ORDER_STATUS_SHIPPED = "shipped";
    public static final String ORDER_STATUS_READY_TO_SHIP = "ready_to_ship";
    public static final String ORDER_STATUS_WRONG = "almost-already";
    public static final String ORDER_SUB_STATUS_NULL = null;
    public static final String ORDER_SUB_STATUS_LOST = "lost";
    public static final String ORDER_SUB_STATUS_PRINTED = "printed";
    public static final String ORDER_SUB_STATUS_WRONG = "printed_in_color";
    public static final String ORDER_SUB_STATUS_SHIPPED_NULL_MSG = "En Camino";
    public static final String ORDER_SUB_STATUS_LOST_MSG = "Perdido";

    public static String packageRequestDtoAsJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(buildPackageRequestDto());
    }

    public static String packageResponseDtoAsJson(final String message) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(buildPackageResponseDto(message));
    }

    public static PackageRequestDTO buildPackageRequestDtoEmptyStatus() {
        final PackageRequestDTO dto = new PackageRequestDTO();
        dto.setId(ORDER_ID);
        dto.setInputs(Collections.emptyList());
        return dto;
    }

    public static PackageRequestDTO buildPackageRequestDtoBlankStatus() {
        return getPackageRequestDTO(VALUE_EMPTY, VALUE_EMPTY);
    }

    public static PackageRequestDTO buildPackageRequestDtoWrongStatus() {
        return getPackageRequestDTO(ORDER_STATUS_WRONG, VALUE_EMPTY);
    }


    public static PackageRequestDTO buildPackageRequestDtoWrongSubStatus() {
        return getPackageRequestDTO(ORDER_STATUS_DELIVERED, ORDER_SUB_STATUS_WRONG);
    }

    public static PackageRequestDTO buildPackageRequestDtoDisorderStatus() {
        final List<StateOrderRequestDTO> inputs = new ArrayList<>();
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_NOT_DELIVERED, ORDER_SUB_STATUS_LOST));
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_SHIPPED, null));
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_READY_TO_SHIP, ORDER_SUB_STATUS_PRINTED));
        final PackageRequestDTO dto = new PackageRequestDTO();
        dto.setId(ORDER_ID);
        dto.setInputs(inputs);
        return dto;
    }

    public static PackageRequestDTO buildPackageRequestDtoInOrderStatus() {
        final List<StateOrderRequestDTO> inputs = new ArrayList<>();
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_READY_TO_SHIP, ORDER_SUB_STATUS_PRINTED));
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_SHIPPED, null));
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_NOT_DELIVERED, ORDER_SUB_STATUS_LOST));
        final PackageRequestDTO dto = new PackageRequestDTO();
        dto.setId(ORDER_ID);
        dto.setInputs(inputs);
        return dto;
    }

    private static PackageRequestDTO buildPackageRequestDto() {
        return getPackageRequestDTO(ORDER_STATUS_DELIVERED, ORDER_SUB_STATUS_NULL);
    }

    private static StateOrderRequestDTO buildStateOrderRequestDto(final String status, final String subStatus) {
        return new StateOrderRequestDTO(status, subStatus);
    }

    private static PackageResponseDTO buildPackageResponseDto(final String message) {
        return new PackageResponseDTO(message);
    }

    private static PackageRequestDTO getPackageRequestDTO(final String status, final String subStatus) {
        final PackageRequestDTO dto = new PackageRequestDTO();
        dto.setId(TestUtils.ORDER_ID);
        dto.setInputs(Collections.singletonList(buildStateOrderRequestDto(status, subStatus)));
        return dto;
    }
}
