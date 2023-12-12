package ru.khachidze.backend.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.khachidze.backend.api.dto.AuthorizedUserDto;
import ru.khachidze.backend.api.dto.FriendRequestDto;
import ru.khachidze.backend.api.service.FriendService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@Slf4j
@RequestMapping("/api")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/addFriend")
    public void addFriend(@Valid @RequestBody FriendRequestDto friendRequestDto) {
        log.info("Пользователь {}, отправил заявку в друзья пользователю {}", friendRequestDto.getUserId(), friendRequestDto.getFriendId());
        friendService.addFriend(friendRequestDto.getUserId(), friendRequestDto.getFriendId());
    }

    @PostMapping("/delFriend")
    public void delFriend(@Valid @RequestBody FriendRequestDto friendRequestDto) {
        log.info("Пользователь {}, удалил из друзей пользователя {}", friendRequestDto.getUserId(), friendRequestDto.getFriendId());
        friendService.removeFriend(friendRequestDto.getUserId(), friendRequestDto.getFriendId());
    }

    @GetMapping("/friendAll")
    public List<AuthorizedUserDto> getUsers(@RequestParam("id") Long id) {
        log.info("получили всех друзей");
        return friendService.getFriendsOfUser(id);
    }
}
