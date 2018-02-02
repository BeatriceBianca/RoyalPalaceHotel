package com.hotel.royalpalace.model.info;

import java.util.Date;

public class UserInfo {

    private String lastName;
    private String firstName;
    private String userRole;
    private Date birthDate;
    private Date hireDate;
    private String phone;
    private String userEmail;
    private String password;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo(){

    }

    public UserInfo(String lastName, String firstName, String userRole, Date birthDate, Date hireDate, String phone, String userEmail, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.userRole = userRole;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.phone = phone;
        this.userEmail = userEmail;
        this.password = password;
    }
}
