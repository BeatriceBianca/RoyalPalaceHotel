package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.repository.UserRepository;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getByUserEmail(String email) {
        return userRepository.getByUserEmail(email);
    }
}
