package ru.khachidze.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpSupportRequestDto {
    @NotBlank(message = "The question should not be empty")
    private String question;
}
