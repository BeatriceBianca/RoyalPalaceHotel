package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.Guest;

public interface GuestService {

    Guest getByIdNumber(String idNumber);
}
