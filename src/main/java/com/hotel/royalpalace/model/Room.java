package com.hotel.royalpalace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HOTEL_ROOMS")
public class Room {

    @ManyToOne()
    @JoinColumn(name = "ROOM_TYPE")
    private RoomType roomType;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number")
    private int roomNumber;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "room")
    private Set<ChosenRooms> chosenRooms = new HashSet<>();

    public RoomType getRoomType() { return roomType; }

    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getRoomNumber() { return roomNumber; }

    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }

    public Set<ChosenRooms> getChosenRooms() {
        return chosenRooms;
    }

    public void setChosenRooms(Set<ChosenRooms> chosenRooms) {
        this.chosenRooms = chosenRooms;
    }

    public Room() {}

    public Room(RoomType roomType, int roomNumber) {
        this.roomType = roomType;
        this.roomNumber = roomNumber;
    }
}
