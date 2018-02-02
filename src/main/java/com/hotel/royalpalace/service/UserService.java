package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.info.UserInfo;

import java.security.NoSuchAlgorithmException;

public interface UserService {

    User getByUserEmail(String email);

    void createUser(UserInfo userInfo) throws NoSuchAlgorithmException;
}
