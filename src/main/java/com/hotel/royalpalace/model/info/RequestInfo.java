package com.hotel.royalpalace.model.info;

import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class RequestInfo {

    private Long id;
    private User user;
    private Guest customer;
    private String requestDate;
    private String arrivalDate;
    private String departureDate;
    private Set<Room> rooms = new HashSet<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Guest getCustomer() { return customer; }

    public void setCustomer(Guest customer) { this.customer = customer; }

    public String getRequestDate() { return requestDate; }

    public void setRequestDate(String requestDate) { this.requestDate = requestDate; }

    public String getArrivalDate() { return arrivalDate; }

    public void setArrivalDate(String arrivalDate) { this.arrivalDate = arrivalDate; }

    public String getDepartureDate() { return departureDate; }

    public void setDepartureDate(String departureDate) { this.departureDate = departureDate; }

    public Set<Room> getRooms() { return rooms; }

    public void setRooms(Set<Room> rooms) { this.rooms = rooms; }
}