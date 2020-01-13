package com.project.meli.demo.dtos;

import java.util.List;

public class ShippingRequestDTO {
    private String id;
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
