package com.hotel.royalpalace.repository;

import com.hotel.royalpalace.model.Guest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends CrudRepository<Guest, Long>{

    Guest getByGuestEmail(String idNumber);

}
