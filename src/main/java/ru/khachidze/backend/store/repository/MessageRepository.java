package ru.khachidze.backend.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khachidze.backend.store.entity.MessageEntity;
import ru.khachidze.backend.store.entity.UserEntity;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findBySenderOrRecipient(UserEntity user1, UserEntity user2);

    @Query("SELECT DISTINCT m.sender FROM MessageEntity m WHERE m.recipient.id = :userId")
    List<UserEntity> findAllSendersByUserId(@Param("userId") Long userId);
    @Query("SELECT DISTINCT m.recipient FROM MessageEntity m WHERE m.sender.id = :userId")
    List<UserEntity> findAllRecipientsByUserId(@Param("userId") Long userId);
    List<MessageEntity> findDistinctBySenderOrRecipient(UserEntity sender, UserEntity recipient);
    List<MessageEntity> findBySenderAndRecipientOrRecipientAndSenderOrderByRecordingTime(UserEntity sender1, UserEntity recipient1, UserEntity sender2, UserEntity recipient2);
    @Modifying
    @Query("DELETE FROM MessageEntity m WHERE (m.sender = :user1 AND m.recipient = :user2) OR (m.sender = :user2 AND m.recipient = :user1)")
    void deleteConversation(@Param("user1") UserEntity user1, @Param("user2") UserEntity user2);
}
