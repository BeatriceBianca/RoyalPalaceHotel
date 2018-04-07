package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.repository.GuestRepository;
import com.hotel.royalpalace.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    GuestRepository guestRepository;

    @Override
    public Guest getByGuestEmail(String email) {
        return guestRepository.getByGuestEmail(email);
    }

    @Override
    public void newGuest(Guest guest) {
        guestRepository.save(guest);
    }

    @Override
    public List<Guest> getAll() {
        return (List<Guest>) guestRepository.findAll();
    }
}
