package com.example.lab4;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
//public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {
    //List<AddressBook> findByName(String name);

    AddressBook findById(long id);
}
