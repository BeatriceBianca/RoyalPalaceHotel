package com.hotel.royalpalace.model;

import javax.persistence.*;

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

    public RoomType getRoomType() { return roomType; }

    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getRoomNumber() { return roomNumber; }

    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }

    public Room() {}

    public Room(RoomType roomType, int roomNumber) {
        this.roomType = roomType;
        this.roomNumber = roomNumber;
    }
}
