package ru.khachidze.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class JwtResponseDto {
    private String token;
}
