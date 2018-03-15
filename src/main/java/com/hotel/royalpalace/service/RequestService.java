package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.model.Request;

import java.util.Date;
import java.util.Set;

public interface RequestService {

    Set<Request> getAllReservationsBetweenDates(Date arrivalDate, Date departureDate);

    void saveGuest(Guest guest);

    Request saveRequest(Request request);
}
