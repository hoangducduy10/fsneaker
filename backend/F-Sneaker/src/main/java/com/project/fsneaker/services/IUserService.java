package com.project.fsneaker.services;

import com.project.fsneaker.dtos.UserDTO;
import com.project.fsneaker.models.User;

public interface IUserService {

    User createUser(UserDTO userDTO) throws Exception;

    String login(String phoneNumber, String password, Long roleId) throws Exception;

}
