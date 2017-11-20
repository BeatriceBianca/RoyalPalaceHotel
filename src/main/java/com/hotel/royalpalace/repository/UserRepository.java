package com.hotel.royalpalace.repository;

import com.hotel.royalpalace.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User getByUserEmail(String email);
}
