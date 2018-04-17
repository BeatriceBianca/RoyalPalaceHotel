package com.hotel.royalpalace.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hotel.royalpalace.model.info.RequestInfo;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "REQUEST")
public class Request {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Guest customer;

    @Column(name = "request_date")
    private Date requestDate;

    @Column(name = "arrival_date")
    private Date arrivalDate;

    @Column(name = "departure_date")
    private Date departureDate;

    @ManyToMany
    @JoinTable(
            name = "room_request",
            joinColumns = { @JoinColumn(name = "request_id") },
            inverseJoinColumns = { @JoinColumn(name = "room_id") }
    )
    private Set<Room> rooms = new HashSet<>();

    @Column(name = "late_checkout")
    private boolean lateCheckout;

    @Column(name = "lunch")
    private boolean lunch;

    @Column(name = "dinner")
    private boolean dinner;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Guest getCustomer() { return customer; }

    public void setCustomer(Guest customer) { this.customer = customer; }

    public Date getRequestDate() { return requestDate; }

    public void setRequestDate(Date requestDate) { this.requestDate = requestDate; }

    public Date getArrivalDate() { return arrivalDate; }

    public void setArrivalDate(Date arrivalDate) { this.arrivalDate = arrivalDate; }

    public Date getDepartureDate() { return departureDate; }

    public void setDepartureDate(Date departureDate) { this.departureDate = departureDate; }

    public Set<Room> getRooms() { return rooms; }

    public void setRooms(Set<Room> rooms) { this.rooms = rooms; }

    public boolean getLateCheckout() { return lateCheckout; }

    public void setLateCheckout(boolean lateCheckout) { this.lateCheckout = lateCheckout; }

    public boolean getLunch() { return lunch; }

    public void setLunch(boolean lunch) { this.lunch = lunch; }

    public boolean getDinner() { return dinner; }

    public void setDinner(boolean dinner) { this.dinner = dinner; }

    public Request() {}

    public Request(RequestInfo request) throws ParseException {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        this.id = request.getId();
        this.user = request.getUser();
        this.customer = request.getCustomer();
        this.arrivalDate = df.parse(request.getArrivalDate());
        this.departureDate = df.parse(request.getDepartureDate());
        this.requestDate = df.parse(request.getRequestDate());
        this.rooms = request.getRooms();
        this.lateCheckout = request.getLateCheckout();
        this.lunch = request.getLunch();
        this.dinner = request.getDinner();
    }
}
