package com.example.lab4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AddressBookViewController {

    @Autowired
    private AddressBookRepository repository;

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
