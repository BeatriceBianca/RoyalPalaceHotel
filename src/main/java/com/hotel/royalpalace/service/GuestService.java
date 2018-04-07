package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.Guest;

import java.util.List;

public interface GuestService {

    Guest getByGuestEmail(String email);

    void newGuest(Guest guest);

    List<Guest> getAll();
}
