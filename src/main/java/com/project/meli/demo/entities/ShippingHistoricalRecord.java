package com.project.meli.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORICAL_QUERY_SHIPPING")
public class ShippingHistoricalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historical_id")
    private Long id;

    @Column(name = "shipping_code")
    private String shippingCode;

    @Column(name = "shiping_status")
    private String status;

    @Column(name = "shiping_sub_status")
    private String subStatus;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public ShippingHistoricalRecord() {
    }

    public ShippingHistoricalRecord(final String shippingCode, final ShippingMovement shippingMovement) {
        this.dateTime = LocalDateTime.now();
        this.shippingCode = shippingCode;
        this.status = shippingMovement.getStatus().name();
        this.subStatus = shippingMovement.getSubStatus().name();
    }

    public Long getId() {
        return id;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public String getStatus() {
        return status;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setShippingCode(final String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setSubStatus(final String subStatus) {
        this.subStatus = subStatus;
    }

    public void setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
