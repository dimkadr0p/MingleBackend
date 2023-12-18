package ru.khachidze.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.khachidze.backend.store.entity.UserEntity;

@Data
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private String content;
}
