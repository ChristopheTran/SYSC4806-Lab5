package com.example.lab4;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores the contact info for buddies.
 *
 * Author: Christophe Tran (101033777)
 */

@Entity
public class AddressBook {

    @Id
    @GeneratedValue
    private Long id;

    //@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL)
    private List<BuddyInfo> contacts;


    public AddressBook() {
        this.contacts = new ArrayList<BuddyInfo>();

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addBuddy(BuddyInfo buddy) {
        this.contacts.add(buddy);
    }

    public boolean removeBuddy(BuddyInfo buddy) {
        return this.contacts.remove(buddy);
    }

    public boolean removeBuddy(Long id) {
        for (BuddyInfo buddy : this.contacts) {
            if (buddy.getId() == id) {
                this.contacts.remove(buddy);
                return true;
            }
        }

        return false;
    }

    public void printContacts() {
        for (BuddyInfo buddy : this.contacts) {
            System.out.println("Name: " + buddy.getName() + ", Phone: " + buddy.getNumber());
        }
    }

    public List<BuddyInfo> getAddressBook() {
        return this.contacts;
    }

    @Override
    public String toString() {
        return String.format("AddressBook[id=%d]", id);
    }

}

