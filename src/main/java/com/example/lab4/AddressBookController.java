package com.example.lab4;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@CrossOrigin(origins = "*")
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
    BuddyInfo addBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddy) {
        AddressBook book = repository.findById(id)
                .orElseThrow(() -> new AddressBookNotFoundException(id));

        book.addBuddy(buddy);
        repository.save(book);
        //return repository.save(book); This returns the address book with entire buddy list

        // Have to do it this way so that the buddy returned has an id value.
//        for (BuddyInfo b : book.getAddressBook()) {
//            if (b.getName().equals(buddy.getName())) {
//                return b;
//            }
//        }
//        return null;
        return buddy;
    }

    @GetMapping("/books/{id}")
    AddressBook one(@PathVariable Long id) {
        //return repository.findById(id).get();
        System.out.println("YOOOO");

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
        return "Hello, World!!!!!";
    }
}
