package ru.khachidze.backend.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizedUserDto {
    private Long id;
    private String name;
    private String email;
    private boolean isOnline;
}
