package ru.khachidze.backend.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.khachidze.backend.api.enums.FriendStatus;

import javax.persistence.*;
import java.util.Date;



@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "friends")
public class FriendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private UserEntity friend;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FriendStatus status;

    @Column(name = "recording_time", nullable = false)
    private Date recordingTime;

}


