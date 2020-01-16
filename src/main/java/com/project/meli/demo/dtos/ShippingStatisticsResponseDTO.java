package com.project.meli.demo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShippingStatisticsResponseDTO {

    @JsonProperty("successful_requests")
    private Long successfulRequests;
    @JsonProperty("error_requests")
    private Long errorRequests;
    @JsonProperty("total_request")
    private Long totalRequests;
    @JsonProperty("date_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;
    @JsonProperty("date_to")
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
