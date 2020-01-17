package com.project.meli.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.dtos.ShippingResponseDTO;
import com.project.meli.demo.dtos.ShippingStatisticsResponseDTO;
import com.project.meli.demo.dtos.StateShippingRequestDTO;
import com.project.meli.demo.entities.ShippingMovement;
import com.project.meli.demo.entities.ShippingStatisticEntity;
import com.project.meli.demo.entities.ShippingStatus;
import com.project.meli.demo.entities.ShippingSubStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtils {

    public static final String VALUE_EMPTY = "";
    public static final String SHIPPING_ID = "28123B";
    public static final String SHIPPING_STATUS_DELIVERED = "delivered";
    public static final String SHIPPING_STATUS_NOT_DELIVERED = "not_delivered";
    public static final String SHIPPING_STATUS_SHIPPED = "shipped";
    public static final String SHIPPING_STATUS_READY_TO_SHIP = "ready_to_ship";
    public static final String SHIPPING_STATUS_READY_TO_SHIP_DESC = "READY TO SHIP";
    public static final String SHIPPING_SUB_STATUS_READY_TO_SHIP_DESC = "READY TO PRINT";
    public static final String SHIPPING_STATUS_WRONG = "almost-already";
    public static final String SHIPPING_SUB_STATUS_DELIVERED_NULL = null;
    public static final String SHIPPING_SUB_STATUS_NULL_MSG = "Entregado";
    public static final String SHIPPING_SUB_STATUS_LOST = "lost";
    public static final String SHIPPING_SUB_STATUS_PRINTED = "printed";
    public static final String SHIPPING_SUB_STATUS_WRONG = "printed_in_color";
    public static final String SHIPPING_SUB_STATUS_SHIPPED_NULL_MSG = "En Camino";
    public static final String SHIPPING_SUB_STATUS_LOST_MSG = "Perdido";
    public static final String HEALTH_MSG_OK = "I'm alive!";
    public static final String PARAM_DATE_FROM = "20200114";
    public static final String PARAM_DATE_TO = "20200117";
    public static final Long SUCCESSFUL_REQUESTS = 1L;
    public static final Long ERROR_REQUESTS = 0L;
    public static final Long TOTAL_REQUESTS = 1L;

    public static String packageRequestDtoAsJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(buildPackageRequestDto());
    }

    public static String packageResponseDtoAsJson(final String message) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(buildPackageResponseDto(message));
    }

    public static String objectResponseAsJson(final Object value) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(value);
    }

    public static ShippingRequestDTO buildPackageRequestDtoEmptyStatus() {
        return new ShippingRequestDTO(SHIPPING_ID, Collections.emptyList());
    }

    public static ShippingRequestDTO buildPackageRequestDtoBlankStatus() {
        return getPackageRequestDTO(VALUE_EMPTY, VALUE_EMPTY);
    }

    public static ShippingRequestDTO buildPackageRequestDtoWrongStatus() {
        return getPackageRequestDTO(SHIPPING_STATUS_WRONG, VALUE_EMPTY);
    }

    public static ShippingRequestDTO buildPackageRequestDtoWrongSubStatus() {
        return getPackageRequestDTO(SHIPPING_STATUS_DELIVERED, SHIPPING_SUB_STATUS_WRONG);
    }

    public static ShippingRequestDTO buildPackageRequestDtoSubStatusNotBelongStatus() {
        return getPackageRequestDTO(SHIPPING_STATUS_DELIVERED, SHIPPING_SUB_STATUS_PRINTED);
    }

    public static ShippingRequestDTO buildPackageRequestDtoWrongStatusAndSubStatusNull() {
        return getPackageRequestDTO(SHIPPING_STATUS_READY_TO_SHIP, null);
    }

    public static ShippingRequestDTO buildPackageRequestDtoDisorderStatus() {
        final List<StateShippingRequestDTO> inputs = new ArrayList<>();
        inputs.add(buildStateShippingRequestDto(SHIPPING_STATUS_NOT_DELIVERED, SHIPPING_SUB_STATUS_LOST));
        inputs.add(buildStateShippingRequestDto(SHIPPING_STATUS_SHIPPED, null));
        inputs.add(buildStateShippingRequestDto(SHIPPING_STATUS_READY_TO_SHIP, SHIPPING_SUB_STATUS_PRINTED));

        return new ShippingRequestDTO(SHIPPING_ID, inputs);
    }

    public static ShippingRequestDTO buildPackageRequestDtoInOrderStatus() {
        final List<StateShippingRequestDTO> inputs = new ArrayList<>();
        inputs.add(buildStateShippingRequestDto(SHIPPING_STATUS_READY_TO_SHIP, SHIPPING_SUB_STATUS_PRINTED));
        inputs.add(buildStateShippingRequestDto(SHIPPING_STATUS_SHIPPED, null));
        inputs.add(buildStateShippingRequestDto(SHIPPING_STATUS_NOT_DELIVERED, SHIPPING_SUB_STATUS_LOST));
        return new ShippingRequestDTO(SHIPPING_ID, inputs);
    }

    public static String packageRequestDtoFailAsJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(getPackageRequestFailDTO());
    }

    public static ShippingStatisticsResponseDTO buildShippingStatisticsResponseDTO() {
        return new ShippingStatisticsResponseDTO(SUCCESSFUL_REQUESTS, ERROR_REQUESTS, TOTAL_REQUESTS, null, null);
    }

    public static ShippingStatisticEntity buildShippingStatisticEntity() {
        final ShippingStatisticEntity entity = new ShippingStatisticEntity();
        entity.setTotalRequests(TOTAL_REQUESTS);
        entity.setErrorRequests(ERROR_REQUESTS);
        entity.setStatisticId(1);
        entity.setSuccessfulRequests(SUCCESSFUL_REQUESTS);
        return entity;
    }

    public static ShippingMovement buildShippingMovementSubStatusNull() {
        return new ShippingMovement(ShippingStatus.HANDLING, ShippingSubStatus.HANDLING_NULL);
    }

    public static ShippingMovement buildShippingMovementSubStatusNotNull() {
        return new ShippingMovement(ShippingStatus.READY_TO_SHIP, ShippingSubStatus.READY_TO_PRINT);
    }

    private static ShippingRequestDTO getPackageRequestFailDTO() {
        return new ShippingRequestDTO(null,
                Collections.singletonList(buildStateShippingRequestDto(SHIPPING_STATUS_DELIVERED, SHIPPING_SUB_STATUS_DELIVERED_NULL)));
    }

    private static ShippingRequestDTO buildPackageRequestDto() {
        return getPackageRequestDTO(SHIPPING_STATUS_DELIVERED, SHIPPING_SUB_STATUS_DELIVERED_NULL);
    }

    private static StateShippingRequestDTO buildStateShippingRequestDto(final String status, final String subStatus) {
        return new StateShippingRequestDTO(status, subStatus);
    }

    private static ShippingResponseDTO buildPackageResponseDto(final String message) {
        return new ShippingResponseDTO(message);
    }

    private static ShippingRequestDTO getPackageRequestDTO(final String status, final String subStatus) {
        return new ShippingRequestDTO(SHIPPING_ID, Collections.singletonList(buildStateShippingRequestDto(status, subStatus)));
    }
}
