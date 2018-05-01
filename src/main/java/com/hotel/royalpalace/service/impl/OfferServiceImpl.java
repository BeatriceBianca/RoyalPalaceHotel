package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.model.Offer;
import com.hotel.royalpalace.repository.OfferRepository;
import com.hotel.royalpalace.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    OfferRepository offerRepository;

    @Override
    public void newOffer(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public List<Offer> getAllOffers() {
        return (List<Offer>) offerRepository.findAll();
    }

}
