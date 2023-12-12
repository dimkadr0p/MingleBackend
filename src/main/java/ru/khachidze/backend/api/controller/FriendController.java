package ru.khachidze.backend.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.khachidze.backend.api.dto.AuthorizedUserDto;
import ru.khachidze.backend.api.service.FriendService;
import ru.khachidze.backend.store.repository.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
@Slf4j
@RequestMapping("/api")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addFriend")
    public void addFriend(Principal principal, @Valid @RequestParam("name") String friendName) {
        log.info("Пользователь {}, добавил себе в контакты пользователя {}", principal.getName(), friendName);
        friendService.addFriend(principal.getName(), friendName);
    }

    @PostMapping("/delFriend")
    public void delFriend(Principal principal, @Valid @RequestParam("name") String friendName) {
        log.info("Пользователь {}, удалил из своих контактов пользователя {}", principal.getName(), friendName);
        friendService.removeFriend(principal.getName(), friendName);
    }

    @GetMapping("/friendAll")
    public List<AuthorizedUserDto> getUsers(@Valid @RequestParam("id") Long id) {
        log.info("получили всех друзей");
        return friendService.getFriendsOfUser(id);
    }
}
