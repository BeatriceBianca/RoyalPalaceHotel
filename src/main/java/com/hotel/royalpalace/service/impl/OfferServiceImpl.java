package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.model.Offer;
import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.model.RoomType;
import com.hotel.royalpalace.repository.OfferRepository;
import com.hotel.royalpalace.repository.RoomRepository;
import com.hotel.royalpalace.repository.RoomTypeRepository;
import com.hotel.royalpalace.service.OfferService;
import com.hotel.royalpalace.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    OfferRepository offerRepository;

    @Override
    public void newOffer(Offer offer) {
        offerRepository.save(offer);
    }
}
