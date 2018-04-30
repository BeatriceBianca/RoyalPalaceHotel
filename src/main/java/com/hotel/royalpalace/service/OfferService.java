package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.Offer;
import com.hotel.royalpalace.model.Promotion;

import java.util.List;

public interface OfferService {

    void newOffer(Offer offer);

    void newPromotion(Promotion promotion);

}
