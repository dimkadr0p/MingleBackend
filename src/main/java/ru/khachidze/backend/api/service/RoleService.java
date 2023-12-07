package ru.khachidze.backend.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khachidze.backend.store.entity.RoleEntity;
import ru.khachidze.backend.store.repository.RoleRepository;


@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity getUserRole() {
        return roleRepository.findByName("user").get();
    }

}
