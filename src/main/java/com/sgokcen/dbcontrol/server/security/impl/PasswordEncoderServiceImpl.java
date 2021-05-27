package com.sgokcen.dbcontrol.server.security.impl;

import com.sgokcen.dbcontrol.server.service.PasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderServiceImpl implements PasswordEncoderService {

    private BCryptPasswordEncoder bcrypt;

    @Autowired
    public PasswordEncoderServiceImpl(BCryptPasswordEncoder bcrypt) {
        this.bcrypt = bcrypt;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return bcrypt.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bcrypt.matches(rawPassword, encodedPassword);
    }



}
