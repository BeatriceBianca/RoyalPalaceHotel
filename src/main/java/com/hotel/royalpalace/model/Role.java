package com.hotel.royalpalace.model;

import com.hotel.royalpalace.model.info.UserInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Beatrice Bianca on 16-Jul-17.
 */

@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
