package com.project.meli.demo.util;

import com.project.meli.demo.dtos.ShippingStatisticsResponseDTO;
import com.project.meli.demo.entities.ShippingStatisticEntity;

import java.time.LocalDate;

import static java.util.Objects.isNull;

public final class MapperUtils {
    public static ShippingStatisticsResponseDTO convertEntityToDto(final ShippingStatisticEntity entity, final LocalDate fromDate,
                                                                   final LocalDate toDate) {
        if (isNull(entity)) {
            return null;
        }
        return new ShippingStatisticsResponseDTO(entity.getSuccessfulRequests(), entity.getErrorRequests(), entity.getTotalRequests(),
                fromDate, toDate);
    }
}
