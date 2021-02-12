package com.example.lab4;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A class that contains the contact info.
 *
 * Author: Christophe Tran (101033777)
 */

@Entity
public class BuddyInfo {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String number;

    public BuddyInfo() {

    }

    public BuddyInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("BuddyInfo[id=%d, name='%s']", id, name);
    }
}

