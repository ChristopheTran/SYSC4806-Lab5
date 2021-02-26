package com.example.lab4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AddressBookViewController {

    @Autowired
    private AddressBookRepository repository;

    @GetMapping("/newAddressBook")
    public String greeting(Model model) {
        return "newAddressBook";
    }

    @PostMapping("/addressbook/newBook")
    public String  newAddressBook(Model model) {
        AddressBook newBook = new AddressBook();
        repository.save(newBook);
        //model.addAttribute("books", repository.findAll());
        model.addAttribute("book", newBook);
        return "newBook";
    }

    @PostMapping("/addressbook/addBuddy")
    public String addBuddy(@RequestParam(value = "bookID") Long bookID, @RequestParam(value = "name") String name,
                           @RequestParam(value = "number") String number, @RequestParam(value = "address") String address,
                           Model model) {
        AddressBook book = repository.findById(bookID)
                .orElseThrow(() -> new AddressBookNotFoundException(bookID));

        BuddyInfo newBuddy = new BuddyInfo(name, number, address);
        book.addBuddy(newBuddy);
        repository.save(book);

        model.addAttribute("book", book);
        model.addAttribute("buddies", book.getAddressBook());
        return "newBook";
    }

    // This was for a previous lab
    @GetMapping("/addressbook")
    public String greeting(@RequestParam(name="id") Long id, Model model) {
        AddressBook book = repository.findById(id).get();
        if (book == null) {
            model.addAttribute("buddies", new ArrayList<BuddyInfo>());
        }
        else {
            model.addAttribute("buddies", book.getAddressBook());
        }
        return "book";
    }

}
