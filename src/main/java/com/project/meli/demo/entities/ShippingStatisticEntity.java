package com.project.meli.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "ShippingStatisticEntity.getStatisticsByDate",
                query = " SELECT 1 AS statistic_id, "
                        + " COUNT(1) as total_requests, "
                        + " IFNULL(SUM((CASE WHEN SHIPPING_STATUS IS NULL THEN 1 ELSE 0 END)), 0) AS error_requests, "
                        + " IFNULL(SUM((CASE WHEN SHIPPING_STATUS IS NOT NULL THEN 1 ELSE 0 END)), 0) AS successful_requests "
                        + " FROM HISTORICAL_QUERY_SHIPPING "
                        + " WHERE DATE_CREATE BETWEEN FORMATDATETIME(IFNULL(:fromDate, DATE_CREATE), 'yyyy-MM-dd 00:00:00') "
                        + " AND FORMATDATETIME(IFNULL(:toDate, DATE_CREATE), 'yyyy-MM-dd 23:59:59') ",
                resultClass = ShippingStatisticEntity.class)})
@Entity
public class ShippingStatisticEntity {

    @Id
    @Column(name = "statistic_id")
    private Integer statisticId;

    @Column(name = "successful_requests")
    private Long successfulRequests;

    @Column(name = "error_requests")
    private Long errorRequests;

    @Column(name = "total_requests")
    private Long totalRequests;


    public ShippingStatisticEntity() {
    }

    public Integer getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(Integer statisticId) {
        this.statisticId = statisticId;
    }

    public Long getSuccessfulRequests() {
        return successfulRequests;
    }

    public Long getErrorRequests() {
        return errorRequests;
    }

    public void setErrorRequests(Long errorRequests) {
        this.errorRequests = errorRequests;
    }

    public Long getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(Long totalRequests) {
        this.totalRequests = totalRequests;
    }

    public void setSuccessfulRequests(Long successfulRequests) {
        this.successfulRequests = successfulRequests;
    }
}
