package com.hotel.royalpalace.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PROMOTIONS")
public class Promotion {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "ROOM_TYPE")
    private RoomType roomType;

    @Column(name = "quantity")
    private int quantity;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public RoomType getRoomType() { return roomType; }

    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Promotion() {}

    public Promotion(String name, String description, RoomType roomType, int quantity) {
        this.name = name;
        this.description = description;
        this.roomType = roomType;
        this.quantity = quantity;
    }
}
