package com.example.lab4;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AddressBookController {
    public final AddressBookRepository repository;

    AddressBookController(AddressBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/books")
    List<AddressBook> all() {
        return (List<AddressBook>) repository.findAll();
    }

    @PostMapping("/books/newBook")
    AddressBook newAddressBook() {
        return repository.save(new AddressBook());
    }

    @PostMapping("/books/addBuddy/{id}")
    AddressBook addBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddy) {
        AddressBook book = repository.findById(id)
                .orElseThrow(() -> new AddressBookNotFoundException(id));

        book.addBuddy(buddy);
        return repository.save(book);

        //return "Successfully added buddy";
    }

    @GetMapping("/books/{id}")
    AddressBook one(@PathVariable Long id) {
        //return repository.findById(id).get();
        System.out.println(repository.findAll());
        return repository.findById(id)
                .orElseThrow(() -> new AddressBookNotFoundException(id));
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/books/removeBuddy/{id}")
    String removeBuddy(@PathVariable Long id, @RequestParam(value = "buddyID") Long buddyId) {
        AddressBook book = repository.findById(id)
                .orElseThrow(() -> new AddressBookNotFoundException(id));

        if (book.removeBuddy(buddyId)) {
            repository.save(book);
            return "Successfully removed buddy";
        }

        return "Failed to remove buddyy";
    }

    @GetMapping("/greeting")
    String greeting() {
        return "Hello, World";
    }
}
