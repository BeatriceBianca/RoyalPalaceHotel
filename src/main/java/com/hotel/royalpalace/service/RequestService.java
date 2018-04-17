package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.ChosenRooms;
import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.model.Request;

import java.util.Date;
import java.util.List;

public interface RequestService {

    List<Request> getAllReservationsBetweenDates(Date arrivalDate, Date departureDate);

    List<ChosenRooms> getAllRChosenRooms();

    void saveGuest(Guest guest);

    Request saveRequest(Request request);

    void saveChosenRoom(ChosenRooms room);

    void removeChosenRoom(ChosenRooms room);

    List<Request> getAll();
}
