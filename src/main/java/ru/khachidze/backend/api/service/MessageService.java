package ru.khachidze.backend.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.khachidze.backend.store.entity.MessageEntity;
import ru.khachidze.backend.store.entity.UserEntity;
import ru.khachidze.backend.store.repository.MessageRepository;
import ru.khachidze.backend.store.repository.UserRepository;

import java.util.Date;
import java.util.List;


@Service
public class MessageService {
    private final MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(UserEntity sender, UserEntity recipient, String content) {
        MessageEntity message = new MessageEntity();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setRecordingTime(new Date());
        message.setRead(false);
        messageRepository.save(message);
    }


    public List<MessageEntity> getAllUserMessages(UserEntity user) {
        return messageRepository.findBySenderOrRecipient(user, user);
    }

    public List<MessageEntity> getMessagesBetweenUsers(UserEntity user1, UserEntity user2) {
        return messageRepository.findBySenderAndRecipientOrRecipientAndSenderOrderByRecordingTime(user1, user2, user1, user2);
    }

    public List<UserEntity> findAllSendersByUserId(Long id) {
        return messageRepository.findAllSendersByUserId(id);
    }

    public List<UserEntity> findAllRecipientsByUserId(Long id) {
        return messageRepository.findAllRecipientsByUserId(id);
    }

    public void setMessageRead(UserEntity sender, UserEntity recipient) {
        List<MessageEntity> messages = messageRepository.findBySenderAndRecipient(sender, recipient);
        for (MessageEntity message : messages) {
            if (!message.isRead()) {
                message.setRead(true);
            }
        }
        messageRepository.saveAll(messages);
    }

    public void deleteConversation(String name, String companion) {
        UserEntity user = userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", companion)
        ));
        UserEntity userСompanion = userRepository.findByName(companion).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", companion)
        ));
        messageRepository.deleteConversation(user, userСompanion);
    }


}
