package ru.khachidze.backend.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khachidze.backend.api.dto.PasswordResetDto;
import ru.khachidze.backend.api.dto.RegistrationUserDto;
import ru.khachidze.backend.api.dto.UserDto;
import ru.khachidze.backend.store.entity.PasswordResetTokenEntity;
import ru.khachidze.backend.store.entity.UserEntity;
import ru.khachidze.backend.store.repository.PasswordResetTokenRepository;
import ru.khachidze.backend.store.repository.RoleRepository;
import ru.khachidze.backend.store.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordTokenRepository(PasswordResetTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserEntity> findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<UserDto> getAllUsers() {
        Iterable<UserEntity> usersIterable = userRepository.findAll();
        List<UserDto> usersDtoList = new ArrayList<>();
        for (UserEntity user : usersIterable) {
            usersDtoList.add(new UserDto(user.getId(), user.getName(), user.getEmail()));
        }
        return usersDtoList;
    }

    public Optional<UserEntity> findByEmail(String name) {
        return userRepository.findByEmail(name);
    }

    public boolean updateUserPassword(String token, String newPassword) {

        Optional<UserEntity> userOptional  = this.getUserByPasswordResetToken(token);

        if (userOptional .isPresent()) {
            UserEntity user = userOptional.get();

            user.setPassword(passwordEncoder.encode(newPassword));

            userRepository.save(user);

            return true;
        } else {
            return false;
        }
    }

    public void deleteTokenResetPassword(String token) {
        Optional<PasswordResetTokenEntity> passwordResetToken = passwordTokenRepository.findByToken(token);
        passwordResetToken.ifPresent(passwordTokenRepository::delete);
    }

    public Optional<PasswordResetTokenEntity> getPasswordResetToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }

    public void createPasswordResetTokenForUser(UserEntity user, String token) {
        PasswordResetTokenEntity myToken = new PasswordResetTokenEntity(token, user);
        passwordTokenRepository.save(myToken);
    }

    public Optional<UserEntity> getUserByPasswordResetToken(String token) {
        Optional<PasswordResetTokenEntity> passwordResetToken = passwordTokenRepository.findByToken(token);
        if (passwordResetToken.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(passwordResetToken.get().getUser());
    }

    public void deleteUser(UserEntity user) {
        Optional<PasswordResetTokenEntity> passwordToken = passwordTokenRepository.findByUser(user);

        passwordToken.ifPresent(passwordTokenRepository::delete);

        userRepository.delete(user);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = findByName(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public UserEntity createNewUser(RegistrationUserDto registrationUserDto) {
        UserEntity user = new UserEntity();
        user.setName(registrationUserDto.getName());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }

    public boolean isUserLoginExists(String name) {
        Optional<UserEntity> user = userRepository.findByName(name);
        return user.isPresent();
    }

    public boolean isUserEmailExists(String name) {
        Optional<UserEntity> user = userRepository.findByEmail(name);
        return user.isPresent();
    }

    public void updateUserLoginTime(String username) {
        userRepository.findByName(username).ifPresent(user -> {
            user.setLastSeen(new Date());
            user.setOnline(true);
        });
    }

}
