package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.model.Request;
import com.hotel.royalpalace.repository.GuestRepository;
import com.hotel.royalpalace.repository.RequestRepository;
import com.hotel.royalpalace.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    GuestRepository guestRepository;

    @Override
    @Transactional(readOnly = true)
    public Set<Request> getAllReservationsBetweenDates(Date arrivalDate, Date departureDate) {
        return requestRepository.getAllReservations();
    }

    @Override
    public void saveGuest(Guest guest) {
        guestRepository.save(guest);
    }

    @Override
    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }
}
