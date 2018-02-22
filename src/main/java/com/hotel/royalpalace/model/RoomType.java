package com.hotel.royalpalace.model;

import javax.persistence.*;

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

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getNrSingleBed() { return nrSingleBed; }

    public void setNrSingleBed(int nrSingleBed) { this.nrSingleBed = nrSingleBed; }

    public int getNrDoubleBed() { return nrDoubleBed; }

    public void setNrDoubleBed(int nrDoubleBed) { this.nrDoubleBed = nrDoubleBed; }

    public String getRoomName() { return roomName; }

    public void setRoomName(String roomName) { this.roomName = roomName; }

    public RoomType () {}
}
