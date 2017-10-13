package com.hotel.royalpalace.repository;

import com.hotel.royalpalace.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Beatrice Bianca on 16-Jul-17.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    User getByUserEmail(String email);
}
