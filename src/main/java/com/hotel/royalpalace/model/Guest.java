package com.hotel.royalpalace.model;

import javax.persistence.*;

/**
 * Created by Beatrice Bianca on 04-Mar-17.
 */

@Entity
@Table(name = "CUSTOMER")
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

    @Column(name = "idNumber")
    private String idNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "customer_phone")
    private String customerPhone;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getIdNumber() { return idNumber; }

    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getCustomerPhone() { return customerPhone; }

    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public Guest() {}

}
