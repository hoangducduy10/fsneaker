package com.project.fsneaker.services;

import com.project.fsneaker.dtos.UserDTO;
import com.project.fsneaker.exceptions.DataNotFoundException;
import com.project.fsneaker.models.User;

public interface IUserService {

    User createUser(UserDTO userDTO) throws DataNotFoundException;

    String login(String phoneNumber, String password);

}