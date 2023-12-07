package ru.khachidze.backend.api.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khachidze.backend.store.repository.PasswordResetTokenRepository;

import java.time.Instant;
import java.util.Date;

@Service
@Transactional
@Slf4j
public class TokenPurgeTaskService {
    @Autowired
    PasswordResetTokenRepository passwordTokenRepository;

    @Scheduled(fixedDelay = 300000) //TODO:Разобраться с шидулингом
    //@Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {

        Date now = Date.from(Instant.now());
        log.info("Сработал шидулер для удаление просроченных токенов");
        passwordTokenRepository.deleteAllExpiredSince(now);
    }
}
