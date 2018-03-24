package com.hotel.royalpalace.model;

import javax.persistence.*;

@Entity
@Table(name = "CHOSEN_ROOMS")
public class ChosenRooms {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "room_id")
    private Room room;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }

    public ChosenRooms () {}

    public ChosenRooms(Room room) {
        this.room = room;
    }
}
