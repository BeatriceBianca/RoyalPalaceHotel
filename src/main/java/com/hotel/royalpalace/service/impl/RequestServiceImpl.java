package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.model.ChosenRooms;
import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.model.Request;
import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.repository.ChosenRoomRepository;
import com.hotel.royalpalace.repository.GuestRepository;
import com.hotel.royalpalace.repository.RequestRepository;
import com.hotel.royalpalace.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    ChosenRoomRepository chosenRoomRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Request> getAllReservationsBetweenDates(Date arrivalDate, Date departureDate) {
        return requestRepository.findAllByArrivalDateLessThanEqualAndDepartureDateGreaterThanEqual(departureDate, arrivalDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChosenRooms> getAllRChosenRooms() {

//        chosenRoomRepository.deleteAll();
        return chosenRoomRepository.findAll();
    }

    @Override
    public void saveGuest(Guest guest) {
        guestRepository.save(guest);
    }

    @Override
    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void saveChosenRoom(ChosenRooms room) {
            chosenRoomRepository.save(room);
    }

    @Override
    public void removeChosenRoom(ChosenRooms room) {
        chosenRoomRepository.delete(chosenRoomRepository.findByRoom(room.getRoom()).get(0));
    }

    @Override
    public List<Request> getAll() {
        return (List<Request>) requestRepository.findAll();
    }
}
