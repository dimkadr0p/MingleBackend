package ru.khachidze.backend.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.khachidze.backend.store.entity.PasswordResetTokenEntity;
import ru.khachidze.backend.store.entity.UserEntity;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {

    Optional<PasswordResetTokenEntity> findByToken(String token);

    Optional<PasswordResetTokenEntity> findByUser(UserEntity user);

    Stream<PasswordResetTokenEntity> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from PasswordResetTokenEntity t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
