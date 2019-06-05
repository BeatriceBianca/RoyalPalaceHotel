package com.hotel.royalpalace.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Beatrice Bianca on 04-Mar-17.
 */

@Entity
@Table(name = "GUEST")
public class Guest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "address")
    private String address;

    @Column(name = "guest_phone")
    private String guestPhone;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "register_date")
    private Date registerDate;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "customer")
    private Set<Request> requests = new HashSet<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getGuestPhone() { return guestPhone; }

    public void setGuestPhone(String guestPhone) { this.guestPhone = guestPhone; }

    public String getGuestEmail() { return guestEmail; }

    public void setGuestEmail(String guestEmail) { this.guestEmail = guestEmail; }

    public Date getRegisterDate() { return registerDate; }

    public void setRegisterDate(Date registerDate) { this.registerDate = registerDate; }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public Guest() {}

    public Guest(String lastName, String firstName, String address, String guestPhone, String guestEmail, String guestPassword) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.guestPhone = guestPhone;
        this.guestEmail = guestEmail;
        this.registerDate = new Date();
    }
}
