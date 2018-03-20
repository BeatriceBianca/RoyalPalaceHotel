package com.hotel.royalpalace.repository;

import com.hotel.royalpalace.model.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    @Query(value = "SELECT DISTINCT r FROM Request r")
    Set<Request> getAllReservations();

    List<Request> findAllByArrivalDateLessThanEqualAndDepartureDateGreaterThanEqual(Date departureDate, Date arrivalDate);
}
