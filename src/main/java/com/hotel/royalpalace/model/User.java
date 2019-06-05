package com.hotel.royalpalace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.royalpalace.model.info.UserInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Beatrice Bianca on 16-Jul-17.
 */

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role", referencedColumnName = "id")
    private Role userRole;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "departure_date")
    private Date departureDate;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private Set<Request> requests = new HashSet<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public Date getBirthDate() { return birthDate; }

    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public Date getHireDate() { return hireDate; }

    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }

    public Date getDepartureDate() { return departureDate; }

    public void setDepartureDate(Date departureDate) { this.departureDate = departureDate; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getUserEmail() { return userEmail; }

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPassword() { return userPassword; }

    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public User() {}

    public User(UserInfo userInfo) {
        this.lastName = userInfo.getLastName();
        this.firstName = userInfo.getFirstName();
        this.userRole = userInfo.getUserRole();
        this.birthDate = userInfo.getBirthDate();
        this.hireDate = userInfo.getHireDate();
        this.phone = userInfo.getPhone();
        this.userEmail = userInfo.getUserEmail();
        this.userPassword = userInfo.getPassword();
    }
}
