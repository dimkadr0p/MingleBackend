package ru.khachidze.backend.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khachidze.backend.api.enums.FriendStatus;
import ru.khachidze.backend.api.exception.UserNotFoundException;
import ru.khachidze.backend.api.exception.UsernameAlreadyExistsException;
import ru.khachidze.backend.store.entity.FriendEntity;
import ru.khachidze.backend.store.entity.UserEntity;
import ru.khachidze.backend.store.repository.FriendRepository;
import ru.khachidze.backend.store.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    public void addFriend(String name, String friendName) {
        UserEntity user = userRepository.findByName(name).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserEntity friend = userRepository.findByName(friendName).orElseThrow(() -> new UserNotFoundException("Friend not found"));

        friendRepository.findByUserNameAndFriendName(name, friendName)
                .ifPresent((friendEntity) -> {
                    throw new UsernameAlreadyExistsException("The user is already in your contacts");
                });

        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setUser(user);
        friendEntity.setFriend(friend);
        friendEntity.setStatus(FriendStatus.ACTIVE);
        friendEntity.setRecordingTime(new Date());

        friendRepository.save(friendEntity);
    }

    public void removeFriend(String name, String friendName) {
        FriendEntity friendEntity = friendRepository.findByUserNameAndFriendName(name, friendName)
                .orElseThrow(() -> new UserNotFoundException("Friendship not found"));

        friendRepository.delete(friendEntity);
    }


    public List<UserEntity> getFriendsOfUser(Long userId) {
        List<FriendEntity> friends = friendRepository.findByUserIdAndStatus(userId, FriendStatus.ACTIVE);
        List<UserEntity> usersDtoList = new ArrayList<>();

        for (FriendEntity friend : friends) {
            usersDtoList.add(UserEntity.builder().id(friend.getFriend()
                    .getId()).name(friend.getFriend()
                    .getName()).email(friend.getFriend()
                    .getEmail()).lastSeen(friend.getFriend()
                    .getLastSeen())
                    .photo(friend.getFriend().getPhoto())
                    .isOnline(friend.getFriend().isOnline()).build());
        }

        return usersDtoList;
    }

}
