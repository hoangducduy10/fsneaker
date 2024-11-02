package com.project.fsneaker.services;

import com.project.fsneaker.components.JwtTokenUtils;
import com.project.fsneaker.dtos.UpdateUserDTO;
import com.project.fsneaker.dtos.UserDTO;
import com.project.fsneaker.exceptions.DataNotFoundException;
import com.project.fsneaker.exceptions.PermissionDenyException;
import com.project.fsneaker.models.Role;
import com.project.fsneaker.models.User;
import com.project.fsneaker.repositories.RoleRepository;
import com.project.fsneaker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists!");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found!"));
        if(role.getName().toUpperCase().equals(Role.ADMIN)){
            throw new PermissionDenyException("You cannot register an administrator!");
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
        user.setRole(role);
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            user.setPassword(encodePassword);
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid phone number or password!");
        }
        User user = optionalUser.get();
        if(user.getFacebookAccoutId() == 0 && user.getGoogleAccoutId() == 0){
            if(!passwordEncoder.matches(password, user.getPassword())){
                throw new BadCredentialsException("Wrong phone number or password!");
            }
        }
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty() || !roleId.equals(user.getRole().getId())){
            throw new DataNotFoundException("Role not found!");
        }
        if(!optionalUser.get().isActive()){
            throw new DataNotFoundException("User is not active!");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, password, user.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(user);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtil.isTokenExpired(token)){
            throw new Exception("Token is expired!");
        }
        String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if(user.isPresent()){
            return user.get();
        }else {
            throw new DataNotFoundException("User not found!");
        }
    }

    @Transactional
    @Override
    public User updateUser(Long userId, UpdateUserDTO userDTO) throws Exception {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found!"));

        String newPhoneNumber = userDTO.getPhoneNumber();
        if(!existingUser.getPhoneNumber().equals(newPhoneNumber) && userRepository.existsByPhoneNumber(newPhoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists!");
        }
        if(userDTO.getFullName() != null){
            existingUser.setFullname(userDTO.getFullName());
        }
        if(newPhoneNumber != null){
            existingUser.setPhoneNumber(newPhoneNumber);
        }
        if(userDTO.getAddress() != null){
            existingUser.setAddress(userDTO.getAddress());
        }
        if(userDTO.getDateOfBirth() != null){
            existingUser.setDateOfBirth(userDTO.getDateOfBirth());
        }
        if(userDTO.getFacebookAccountId() > 0){
            existingUser.setFacebookAccoutId(userDTO.getFacebookAccountId());
        }
        if(userDTO.getGoogleAccountId() > 0){
            existingUser.setGoogleAccoutId(userDTO.getGoogleAccountId());
        }

        if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
            String newPassword = userDTO.getPassword();
            String encodePassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodePassword);
        }

        return userRepository.save(existingUser);
    }


}
