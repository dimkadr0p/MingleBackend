package ru.khachidze.backend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FriendRequestDto {

    @JsonProperty("user_id")
    @NotBlank(message = "The user_id must be filled in")
    private Long UserId;

    @JsonProperty("friend_id")
    @NotBlank(message = "The friend_id must be filled in")
    private Long FriendId;

}
