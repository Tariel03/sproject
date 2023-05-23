package com.example.sproject.Controllers;

import com.example.sproject.Config.JWTFilter;
import com.example.sproject.Models.User;
import com.example.sproject.Repositories.ClientRepository;
import com.example.sproject.Services.ClientService;
import com.example.sproject.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
  ClientRepository clientRepository;

    @MockBean
    ClientService clientService;


    @Test
    void clientList() throws Exception {
        List<User> clientList = clientService.findAll();
        given(clientService.findAll()).willReturn(clientList);
       mockMvc.perform(get("/user/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(notNull());
    }


    @Test
    @Disabled

    void users() {
    }

    @Test
    @Disabled

    void showUserInfo() {
    }

    @Test
    @Disabled

    void uploadPhoto() {
    }

    @Test
    @Disabled

    void deletePhoto() {
    }

    @Test
    @Disabled

    void editLastname() {
    }

    @Test
    @Disabled

    void editFirstname() {
    }

    @Test
    @Disabled
    void editUsername() {
    }
}