package com.sgokcen.dbcontrol.server.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sgokcen.dbcontrol.server.dto.UserDTO;

public interface UserService extends ModelService<UserDTO>, UserDetailsService {

}
