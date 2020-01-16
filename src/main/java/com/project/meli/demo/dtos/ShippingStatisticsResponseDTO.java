package com.project.meli.demo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShippingStatisticsResponseDTO {

    private Long successfulRequests;
    private Long errorRequests;
    private Long totalRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateTo;

    public ShippingStatisticsResponseDTO(final Long successfulRequests, final Long errorRequests, final Long totalRequests,
                                         final LocalDate dateFrom, final LocalDate dateTo) {
        this.successfulRequests = successfulRequests;
        this.errorRequests = errorRequests;
        this.totalRequests = totalRequests;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getSuccessfulRequests() {
        return successfulRequests;
    }

    public Long getErrorRequests() {
        return errorRequests;
    }

    public Long getTotalRequests() {
        return totalRequests;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
}
