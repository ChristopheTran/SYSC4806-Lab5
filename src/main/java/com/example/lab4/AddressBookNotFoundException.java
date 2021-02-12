package com.example.lab4;

public class AddressBookNotFoundException extends RuntimeException {
    AddressBookNotFoundException(Long id) {
        super("Could not find book " + id);
    }
}
