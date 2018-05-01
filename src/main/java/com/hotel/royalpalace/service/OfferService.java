package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.Offer;

import java.util.List;

public interface OfferService {

    void newOffer(Offer offer);

    List<Offer> getAllOffers();


}
