package ru.khachidze.backend.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SupportResponseToHelp {
    @NotBlank(message = "The response should not be empty")
    String response;
}
