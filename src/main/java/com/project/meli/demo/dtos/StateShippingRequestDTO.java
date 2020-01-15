package com.project.meli.demo.dtos;

import javax.validation.constraints.NotEmpty;

public class StateShippingRequestDTO {
    @NotEmpty(message = "It's mandatory at least a status")
    private final String status;
    private final String substatus;

    public StateShippingRequestDTO(final String status, final String substatus) {
        this.status = status;
        this.substatus = substatus;
    }

    public String getStatus() {
        return status;
    }

    public String getSubstatus() {
        return substatus;
    }
}
