package com.project.meli.demo.dtos;

import java.util.List;

public class PackageRequestDTO {
    private String id;
    private List<StateOrderRequestDTO> inputs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<StateOrderRequestDTO> getInputs() {
        return inputs;
    }

    public void setInputs(List<StateOrderRequestDTO> inputs) {
        this.inputs = inputs;
    }
}
