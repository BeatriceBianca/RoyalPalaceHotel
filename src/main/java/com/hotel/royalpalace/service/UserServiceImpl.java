package com.hotel.royalpalace.service;

import com.hotel.royalpalace.auxiliary.Encryption;
import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * Created by Beatrice Bianca on 16-Jul-17.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public User getUser(String email, String password){

        try {
            User user = userRepository.getByUserEmail(email);
            if (user == null) {
                return null;
            } else {
                if (user.getUserPassword().equals(Encryption.computeMD5(password))) {
                    return user;
                } else {
                    throw new IllegalArgumentException();
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
