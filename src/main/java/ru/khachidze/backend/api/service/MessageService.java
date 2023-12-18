package ru.khachidze.backend.api.service;

import org.springframework.stereotype.Service;
import ru.khachidze.backend.store.entity.MessageEntity;
import ru.khachidze.backend.store.entity.UserEntity;
import ru.khachidze.backend.store.repository.MessageRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(UserEntity sender, UserEntity recipient, String content) {
        MessageEntity message = new MessageEntity();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setRecordingTime(new Date());
        messageRepository.save(message);
    }

    //TODO::Возможно убрать
    public List<UserEntity> getChatUsers(UserEntity user) {
        return messageRepository.findDistinctBySenderOrRecipient(user, user)
                .stream()
                .map(message -> {
                    if (message.getSender().equals(user)) {
                        return message.getRecipient();
                    } else {
                        return message.getSender();
                    }
                })
                .distinct()
                .collect(Collectors.toList());
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

    //антон: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbiIsInJvbGVzIjpbInVzZXIiXSwiZXhwIjoxNzAyODY3MjY0LCJpYXQiOjE3MDI4NjU0NjR9.9sz-xDERXm3MN92J7k81bufwfnt32sz7ZnaLWG2L7-Y

    //Алена: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGVuYSIsInJvbGVzIjpbInVzZXIiXSwiZXhwIjoxNzAyODY5MDIxLCJpYXQiOjE3MDI4NjcyMjF9.3og6y2gqUGdpqLpsqPQhAkKQgP0BN1IDlsD46r3JdHU

}
