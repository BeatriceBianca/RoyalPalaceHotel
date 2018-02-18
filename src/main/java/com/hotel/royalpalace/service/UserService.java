package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.info.UserInfo;

import java.security.NoSuchAlgorithmException;

public interface UserService {

    User getByUserEmail(String email);

    void createUser(UserInfo userInfo) throws NoSuchAlgorithmException;

    void editUser(UserInfo userInfo) throws NoSuchAlgorithmException;

    void changePassword(User user,String newPassword) throws NoSuchAlgorithmException;

}
