package com.example.lab4;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressBookRepository addressBookRepository;

    @Autowired
    private AddressBookRepository repo;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }

    @Test
    public void addressBookShouldReturnMessage() throws Exception {
        AddressBook book = new AddressBook();
        book.addBuddy(new BuddyInfo("Christophe", "123", "123street"));
        List<AddressBook> addressBooks = new ArrayList<AddressBook>() {{add(book);}};

        when(addressBookRepository.findAll()).thenReturn(addressBooks);
        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Christophe")));
    }

    @Test
    public void createAddressBook() throws Exception {
        this.mockMvc.perform(post("/books/newBook"))
                .andDo(print()).andExpect(status().isOk());
    }

//    @Test
//    public void addBuddy() throws Exception {
////        AddressBook book = new AddressBook();
////        List<AddressBook> addressBooks = new ArrayList<AddressBook>() {{add(book);}};
////        when(addressBookRepository.findAll()).thenReturn(addressBooks);
//        this.addressBookRepository.save(new AddressBook());
//
//        BuddyInfo buddy = new BuddyInfo("Chris", "321");
//
//        this.mockMvc.perform(MockMvcRequestBuilders
//                .post("/books/addBuddy/{id}", 1)
//                .content(asJsonString(buddy))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

//    @Test
//    public void findSpecificAddressBook() throws Exception {
//        AddressBook book = new AddressBook();
//
//        repo.save(book);
//        when(repo.findById(1)).thenReturn(book);
//
//        this.mockMvc.perform(get("/books/{id}", "1")).andDo(print()).andExpect(status().isOk());
//    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
