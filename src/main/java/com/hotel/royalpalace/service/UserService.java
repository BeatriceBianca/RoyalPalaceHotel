package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Beatrice Bianca on 16-Jul-17.
 */
public interface UserService {

    @Transactional(readOnly = true)
    User getUser(String email, String password);

    @Transactional(readOnly = true)
    User getByUserEmail(String email);
}
