package com.hotel.royalpalace.model;

import javax.persistence.*;

@Entity
@Table(name = "CHOSEN_ROOMS3")
public class ChosenRooms {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "session_id")
    private String sessionId;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }

    public String getSessionId() { return sessionId; }

    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public ChosenRooms () {}

    public ChosenRooms(Room room) {
        this.room = room;
    }
}
