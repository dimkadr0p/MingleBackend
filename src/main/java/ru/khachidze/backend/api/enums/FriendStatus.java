package ru.khachidze.backend.api.enums;

public enum FriendStatus {
    ACTIVE("активен"),
    INACTIVE("неактивен");

    private final String status;

    FriendStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
