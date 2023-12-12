package ru.khachidze.backend.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khachidze.backend.api.dto.AuthorizedUserDto;
import ru.khachidze.backend.api.enums.FriendStatus;
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


    public void addFriend(Long userId, Long friendId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserEntity friend = userRepository.findById(friendId).orElseThrow(() -> new UsernameNotFoundException("Friend not found"));

        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setUser(user);
        friendEntity.setFriend(friend);
        friendEntity.setStatus(FriendStatus.ACTIVE);
        friendEntity.setRecordingTime(new Date());

        friendRepository.save(friendEntity);
    }

    public void removeFriend(Long userId, Long friendId) {
        FriendEntity friendEntity = friendRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new UsernameNotFoundException("Friendship not found"));

        friendRepository.delete(friendEntity);
    }


    public List<AuthorizedUserDto> getFriendsOfUser(Long userId) {
        List<FriendEntity> friends = friendRepository.findByUserIdAndStatus(userId, FriendStatus.ACTIVE);
        List<AuthorizedUserDto> usersDtoList = new ArrayList<>();

        for (FriendEntity friend : friends) {
            usersDtoList.add(new AuthorizedUserDto(friend.getFriend().getId(), friend.getFriend().getName(), friend.getFriend().getEmail(), friend.getFriend().isOnline()));
        }

        return usersDtoList;
    }

}
