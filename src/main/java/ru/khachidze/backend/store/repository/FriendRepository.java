package ru.khachidze.backend.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khachidze.backend.api.enums.FriendStatus;
import ru.khachidze.backend.store.entity.FriendEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    List<FriendEntity> findByUserIdAndStatus(Long userId, FriendStatus status);

    Optional<FriendEntity> findByUserNameAndFriendName(String name, String friendName);
}