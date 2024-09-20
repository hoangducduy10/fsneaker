package com.project.fsneaker.services;

import com.project.fsneaker.dtos.UserDTO;
import com.project.fsneaker.exceptions.DataNotFoundException;
import com.project.fsneaker.models.Role;
import com.project.fsneaker.models.User;
import com.project.fsneaker.repositories.RoleRepository;
import com.project.fsneaker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists!");
        }
        User user = User.builder()
                .fullname(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccoutId(userDTO.getFacebookAccountId())
                .googleAccoutId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found!"));
        user.setRole(role);
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
//            String password = userDTO.getPassword();
//            user.setPassword(password);
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return null;
    }
}
