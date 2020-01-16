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
public class ShippingHistoricalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historical_id")
    private Long id;

    @Column(name = "shipping_code")
    private String shippingCode;

    @Column(name = "shipping_status")
    private String status;

    @Column(name = "shipping_sub_status")
    private String subStatus;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_update")
    private LocalDateTime dateUpdate;

    public ShippingHistoricalEntity() {
    }

    public ShippingHistoricalEntity(final String shippingCode, final String status, final String subStatus) {
        super();
        this.shippingCode = shippingCode;
        this.status = status;
        this.subStatus = subStatus;
        this.dateCreate = LocalDateTime.now();
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

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(final String subStatus) {
        this.subStatus = subStatus;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
