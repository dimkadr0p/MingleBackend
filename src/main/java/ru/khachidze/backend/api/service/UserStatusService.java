package ru.khachidze.backend.api.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.khachidze.backend.store.entity.UserEntity;
import ru.khachidze.backend.store.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class UserStatusService {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedDelay = 300000)
    public void checkUserStatus() {
        log.info("Сработал шидулер для проверки активности пользователя");

        Iterable<UserEntity> users = userRepository.findAll();
        List<UserEntity> usersToUpdate = new ArrayList<>();

        for (UserEntity user : users) {
            if (!isUserActive(user)) {
                log.info("Пользователь {} не в сети", user.getName());
                user.setOnline(false);
                usersToUpdate.add(user);
            }
        }
        userRepository.saveAll(usersToUpdate);
    }

    private boolean isUserActive(UserEntity user) {
        if(user.getLastSeen() == null) return false;
        Date currentDate = new Date();
        long inactiveDuration = 3 * 60 * 1000; // 3 минут в миллисекундах
        long lastSeenTime = user.getLastSeen().getTime();
        return (currentDate.getTime() - lastSeenTime) <= inactiveDuration;
    }

}
