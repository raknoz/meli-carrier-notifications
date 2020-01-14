package com.project.meli.demo.dtos;

import javax.validation.constraints.NotEmpty;

public class StateShippingRequestDTO {
    @NotEmpty(message = "It's mandatory at least a status")
    private String status;
    private String substatus;

    public StateShippingRequestDTO(final String status, final String substatus) {
        this.status = status;
        this.substatus = substatus;
    }

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
