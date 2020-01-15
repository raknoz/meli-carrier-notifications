package com.project.meli.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippingResponseDTO {

    @JsonProperty("package")
    private final String message;

    public ShippingResponseDTO(final String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
