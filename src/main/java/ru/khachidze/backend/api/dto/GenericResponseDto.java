package ru.khachidze.backend.api.dto;


import lombok.Data;

@Data
public class GenericResponseDto {

    private String message;

    public GenericResponseDto(String message) {
        super();
        this.message = message;
    }

}
