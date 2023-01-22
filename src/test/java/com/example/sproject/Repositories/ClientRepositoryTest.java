package com.example.sproject.Repositories;

import com.example.sproject.Dto.AuthenticationDTO;
import com.example.sproject.Dto.UserDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ClientRepositoryTest {
    @Autowired
    ClientRepository clientRepository;

    @Test
    void findByUsername() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
    }

    @Test
    void existsByUsername() {
    }
}