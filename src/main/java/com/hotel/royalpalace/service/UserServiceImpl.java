package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Beatrice Bianca on 16-Jul-17.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public User getByLastName(String name){
        return userRepository.getByLastName(name);
    }
}
