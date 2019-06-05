package com.hotel.royalpalace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROOMS_TYPE")
public class RoomType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "single_bed")
    private int nrSingleBed;

    @Column(name = "double_bed")
    private int nrDoubleBed;

    @Column(name = "name_room")
    private String roomName;

    @Column(name = "price")
    private float price;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "roomType")
    private Set<Offer> offers = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "roomType")
    private Set<Room> rooms = new HashSet<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getNrSingleBed() { return nrSingleBed; }

    public void setNrSingleBed(int nrSingleBed) { this.nrSingleBed = nrSingleBed; }

    public int getNrDoubleBed() { return nrDoubleBed; }

    public void setNrDoubleBed(int nrDoubleBed) { this.nrDoubleBed = nrDoubleBed; }

    public String getRoomName() { return roomName; }

    public void setRoomName(String roomName) { this.roomName = roomName; }

    public float getPrice() { return price; }

    public void setPrice(float price) { this.price = price; }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public RoomType () {}
}
