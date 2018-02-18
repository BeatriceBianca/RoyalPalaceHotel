package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.auxiliary.Encryption;
import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.info.UserInfo;
import com.hotel.royalpalace.repository.UserRepository;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    private User usr = null;

    @Override
    @Transactional(readOnly = true)
    public User getByUserEmail(String email) {
        return userRepository.getByUserEmail(email);
    }

    @Override
    public void createUser(UserInfo userInfo) throws NoSuchAlgorithmException {

        userInfo.setPassword(Encryption.computeMD5("parola"));
        User user = new User(userInfo);

        try {
            usr = userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editUser(UserInfo userInfo) {

        User user = this.getByUserEmail(userInfo.getUserEmail());

        user.setBirthDate(userInfo.getBirthDate());
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setPhone(userInfo.getPhone());

        try {
            usr = userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePassword(User user, String newPassword) throws NoSuchAlgorithmException {

        user.setUserPassword(Encryption.computeMD5(newPassword));

        try {
            usr = userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}