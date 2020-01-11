package com.project.meli.demo.dtos;

public class StateOrderRequestDTO {
    private String status;
    private String substatus;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubstatus() {
        return substatus;
    }

    public void setSubstatus(String substatus) {
        this.substatus = substatus;
    }
}
