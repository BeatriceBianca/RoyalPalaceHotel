package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.User;

public interface UserService {

    User getByUserEmail(String email);
}
