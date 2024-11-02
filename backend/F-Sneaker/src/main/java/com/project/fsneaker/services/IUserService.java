package com.project.fsneaker.services;

import com.project.fsneaker.dtos.UpdateUserDTO;
import com.project.fsneaker.dtos.UserDTO;
import com.project.fsneaker.models.User;

public interface IUserService {

    User createUser(UserDTO userDTO) throws Exception;

    String login(String phoneNumber, String password, Long roleId) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    User updateUser(Long userId, UpdateUserDTO updateUserDTO) throws Exception;

}
