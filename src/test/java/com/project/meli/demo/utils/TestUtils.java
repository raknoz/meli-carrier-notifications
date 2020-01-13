package com.project.meli.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.dtos.ShippingResponseDTO;
import com.project.meli.demo.dtos.StateShippingRequestDTO;

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
    public static final String HEALTH_MSG_OK = "I am alive!";

    public static String packageRequestDtoAsJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(buildPackageRequestDto());
    }

    public static String packageResponseDtoAsJson(final String message) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(buildPackageResponseDto(message));
    }

    public static ShippingRequestDTO buildPackageRequestDtoEmptyStatus() {
        final ShippingRequestDTO dto = new ShippingRequestDTO();
        dto.setId(ORDER_ID);
        dto.setInputs(Collections.emptyList());
        return dto;
    }

    public static ShippingRequestDTO buildPackageRequestDtoBlankStatus() {
        return getPackageRequestDTO(VALUE_EMPTY, VALUE_EMPTY);
    }

    public static ShippingRequestDTO buildPackageRequestDtoWrongStatus() {
        return getPackageRequestDTO(ORDER_STATUS_WRONG, VALUE_EMPTY);
    }

    public static ShippingRequestDTO buildPackageRequestDtoWrongSubStatus() {
        return getPackageRequestDTO(ORDER_STATUS_DELIVERED, ORDER_SUB_STATUS_WRONG);
    }

    public static ShippingRequestDTO buildPackageRequestDtoSubStatusNotBelongStatus() {
        return getPackageRequestDTO(ORDER_STATUS_DELIVERED, ORDER_SUB_STATUS_PRINTED);
    }

    public static ShippingRequestDTO buildPackageRequestDtoDisorderStatus() {
        final List<StateShippingRequestDTO> inputs = new ArrayList<>();
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_NOT_DELIVERED, ORDER_SUB_STATUS_LOST));
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_SHIPPED, null));
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_READY_TO_SHIP, ORDER_SUB_STATUS_PRINTED));
        final ShippingRequestDTO dto = new ShippingRequestDTO();
        dto.setId(ORDER_ID);
        dto.setInputs(inputs);
        return dto;
    }

    public static ShippingRequestDTO buildPackageRequestDtoInOrderStatus() {
        final List<StateShippingRequestDTO> inputs = new ArrayList<>();
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_READY_TO_SHIP, ORDER_SUB_STATUS_PRINTED));
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_SHIPPED, null));
        inputs.add(buildStateOrderRequestDto(ORDER_STATUS_NOT_DELIVERED, ORDER_SUB_STATUS_LOST));
        final ShippingRequestDTO dto = new ShippingRequestDTO();
        dto.setId(ORDER_ID);
        dto.setInputs(inputs);
        return dto;
    }

    private static ShippingRequestDTO buildPackageRequestDto() {
        return getPackageRequestDTO(ORDER_STATUS_DELIVERED, ORDER_SUB_STATUS_NULL);
    }

    private static StateShippingRequestDTO buildStateOrderRequestDto(final String status, final String subStatus) {
        return new StateShippingRequestDTO(status, subStatus);
    }

    private static ShippingResponseDTO buildPackageResponseDto(final String message) {
        return new ShippingResponseDTO(message);
    }

    private static ShippingRequestDTO getPackageRequestDTO(final String status, final String subStatus) {
        final ShippingRequestDTO dto = new ShippingRequestDTO();
        dto.setId(TestUtils.ORDER_ID);
        dto.setInputs(Collections.singletonList(buildStateOrderRequestDto(status, subStatus)));
        return dto;
    }
}
