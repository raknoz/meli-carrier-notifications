package com.project.meli.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippingResponseDTO {

    @JsonProperty("package")
    private String message;

    public ShippingResponseDTO(final String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
