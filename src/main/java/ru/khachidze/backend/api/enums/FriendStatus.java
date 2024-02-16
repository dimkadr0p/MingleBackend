package ru.khachidze.backend.api.enums;

import lombok.Getter;

@Getter
public enum FriendStatus {
    ACTIVE("активен"),
    INACTIVE("неактивен");

    private final String status;

    FriendStatus(String status) {
        this.status = status;
    }

}
