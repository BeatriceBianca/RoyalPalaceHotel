package com.hotel.royalpalace.model.enums;

public enum Roles {

    MANAGER("Manager"),
    RECEPTIONIST("Receptionist"),
    MAID("Maid");

    private String description;
    Roles(String description) {this.description = description;}

    public String getDescription() { return this.description; }
}
