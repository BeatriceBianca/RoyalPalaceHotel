package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.repository.GuestRepository;
import com.hotel.royalpalace.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    GuestRepository guestRepository;

//    @Override
//    public Guest getByIdNumber(String idNumber) {
//        return guestRepository.getByIdNumber(idNumber);
//    }

    @Override
    public void newGuest(Guest guest) {
        guestRepository.save(guest);
    }
}
