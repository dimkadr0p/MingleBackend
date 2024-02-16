package ru.khachidze.backend.api.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Long id;
    private String content;
}
