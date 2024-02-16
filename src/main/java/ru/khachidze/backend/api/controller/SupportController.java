package ru.khachidze.backend.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.khachidze.backend.api.advice.NotFoundExceptionHandler;
import ru.khachidze.backend.api.dto.HelpSupportRequestDto;
import ru.khachidze.backend.api.dto.SupportResponseToHelp;
import ru.khachidze.backend.api.exception.QuestionNotFoundException;
import ru.khachidze.backend.api.exception.UserNotFoundException;
import ru.khachidze.backend.store.entity.SupportEntity;
import ru.khachidze.backend.store.entity.UserEntity;
import ru.khachidze.backend.store.repository.SupportRepository;
import ru.khachidze.backend.store.repository.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

@RestController
@Validated
@RequestMapping("/api")
public class SupportController {
    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/helpSupport")
    public ResponseEntity<String> sendQuestion(Principal principal, @Valid @RequestBody HelpSupportRequestDto helpSupportRequestDto) {

        UserEntity user = userRepository.findByName(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        SupportEntity supportEntity = new SupportEntity();

        supportEntity.setUser(user);
        supportEntity.setQuestion(helpSupportRequestDto.getQuestion());
        supportEntity.setRecordingTimeQuestion(new Date());

        supportRepository.save(supportEntity);

        return ResponseEntity.ok("Question has been sent");
    }


    @PutMapping("/helpedOut/{id}")
    public ResponseEntity<String> sendResponse(@PathVariable("id") Long id,
                                               @Valid @RequestBody SupportResponseToHelp response) {

        SupportEntity supportEntity = supportRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));

        supportEntity.setResponse(response.getResponse());

        supportEntity.setRecordingTimeResponse(new Date());

        supportRepository.save(supportEntity);

        return ResponseEntity.ok("Response has been sent");
    }



}
