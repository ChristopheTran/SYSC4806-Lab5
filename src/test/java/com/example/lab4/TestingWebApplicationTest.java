package com.example.lab4;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestingWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() throws Exception {
        this.mockMvc.perform(post("/books/newBook"))
                .andDo(print()).andExpect(status().isOk());

        BuddyInfo buddy = new BuddyInfo("Christophe", "321", "Street123");
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/books/addBuddy/{id}", 1)
                .content(asJsonString(buddy))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }

    @Test
    public void addressBookShouldReturnMessage() throws Exception {
        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Christophe")));
    }

    @Test
    public void addBuddy() throws Exception {
        this.mockMvc.perform(post("/books/newBook"))
                .andDo(print()).andExpect(status().isOk());

        BuddyInfo buddy = new BuddyInfo("Ted", "321", "Street123");

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/books/addBuddy/{id}", 1)
                .content(asJsonString(buddy))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ted")));
    }

    @Test
    public void deleteBuddy() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/books/removeBuddy/{id}", 1)
                .param("buddyID", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successfully removed buddy")));
    }

    @Test
    public void findSpecificAddressBook() throws Exception {
        this.mockMvc.perform(get("/books/{id}", "1")).andDo(print()).andExpect(status().isOk());
    }

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
