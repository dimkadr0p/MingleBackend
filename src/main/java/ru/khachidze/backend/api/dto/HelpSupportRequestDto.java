package ru.khachidze.backend.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HelpSupportRequestDto {
    @NotBlank(message = "The question should not be empty")
    private String question;
}
