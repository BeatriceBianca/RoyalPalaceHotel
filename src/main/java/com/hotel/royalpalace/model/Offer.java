package com.hotel.royalpalace.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OFFERS")
public class Offer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne()
    @JoinColumn(name = "ROOM_TYPE")
    private RoomType roomType;

    @Column(name = "quantity")
    private int quantity;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public RoomType getRoomType() { return roomType; }

    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Offer() {}

    public Offer(String description, Date startDate, Date endDate, RoomType roomType, int quantity) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomType = roomType;
        this.quantity = quantity;
    }
}
