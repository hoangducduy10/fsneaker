package com.project.fsneaker.services;

import com.project.fsneaker.models.Role;
import com.project.fsneaker.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllCategories() {
        return roleRepository.findAll();
    }
}
