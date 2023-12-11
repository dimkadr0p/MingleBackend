package ru.khachidze.backend.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khachidze.backend.api.enums.FriendStatus;
import ru.khachidze.backend.store.entity.FriendEntity;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    List<FriendEntity> findByUserIdAndStatus(int userId, FriendStatus status);
}