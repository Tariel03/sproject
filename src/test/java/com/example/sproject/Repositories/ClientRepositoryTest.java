package com.example.sproject.Repositories;

import com.example.sproject.Dto.AuthenticationDTO;
import com.example.sproject.Dto.UserDTO;
import com.example.sproject.Models.User;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ClientRepositoryTest {
    ClientRepository clientRepository;
    ModelMapper modelMapper;
    @Autowired
    public ClientRepositoryTest(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Test
    void findByUsername() {
        UserDTO userDTO = new UserDTO("tariel","nurlanovich","Tariel","Akmatov");
        User user = convertToUser(userDTO);
        user.setData(null);
        clientRepository.save(user);
        assertEquals(user, clientRepository.findByUsername("tariel").get());

    }

    @Test
    void existsByUsername() {
        UserDTO userDTO = new UserDTO("tariel","nurlanovich","Tariel","Akmatov");
        User user = convertToUser(userDTO);
        user.setData(null);
        clientRepository.save(user);
        assertTrue(clientRepository.existsByUsername("tariel"));
    }


    public User convertToUser(UserDTO userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

}