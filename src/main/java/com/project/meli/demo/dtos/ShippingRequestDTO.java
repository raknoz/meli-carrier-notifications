package com.project.meli.demo.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ShippingRequestDTO {
    @NotNull(message = "It's mandatory has a shipping code")
    private String id;

    @NotEmpty(message = "It's mandatory has at least a status")
    private List<StateShippingRequestDTO> inputs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<StateShippingRequestDTO> getInputs() {
        return inputs;
    }

    public void setInputs(List<StateShippingRequestDTO> inputs) {
        this.inputs = inputs;
    }
}
