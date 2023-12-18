package ru.khachidze.backend.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khachidze.backend.api.dto.MessageDto;
import ru.khachidze.backend.api.exception.UserNotFoundException;
import ru.khachidze.backend.api.service.MessageService;
import ru.khachidze.backend.store.entity.MessageEntity;
import ru.khachidze.backend.store.entity.UserEntity;
import ru.khachidze.backend.store.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(Principal principal, @RequestBody MessageDto messageDto) {
        UserEntity sender = userRepository.findByName(principal.getName()).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserEntity recipient = userRepository.findById(messageDto.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        String content = messageDto.getContent();
        messageService.sendMessage(sender, recipient, content);
        return ResponseEntity.ok("Message sent successfully");
    }


    @GetMapping("/dialogs")
    public ResponseEntity<List<MessageEntity>>  getUserDialogs(Principal principal) {
        UserEntity user = userRepository.findByName(principal.getName()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return ResponseEntity.ok(messageService.getAllUserMessages(user));
    }


    @GetMapping("/users/chats")
    public ResponseEntity<List<UserEntity>> getChatUsers(Principal principal) {
        UserEntity currentUser = userRepository.findByName(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return ResponseEntity.ok(messageService.getChatUsers(currentUser));
    }


    @GetMapping("/messages")
    public ResponseEntity<List<MessageEntity>> getMessagesBetweenUsers(Principal principal, @RequestParam("id") Long userId) {
        UserEntity user1 = userRepository.findByName(principal.getName()).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserEntity user2 = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<MessageEntity> messages = messageService.getMessagesBetweenUsers(user1, user2);
        return ResponseEntity.ok(messages);
    }


}