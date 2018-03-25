package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.ChosenRooms;
import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.model.Request;
import com.hotel.royalpalace.model.Room;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface RequestService {

    List<Request> getAllReservationsBetweenDates(Date arrivalDate, Date departureDate);

    List<ChosenRooms> getAllRChosenRooms();

    void saveGuest(Guest guest);

    Request saveRequest(Request request);

    void saveChosenRoom(ChosenRooms room);

    void removeChosenRoom(ChosenRooms room);

    List<Request> getAll();
}
