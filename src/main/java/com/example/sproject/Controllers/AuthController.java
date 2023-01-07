package com.example.sproject.Controllers;

import com.example.sproject.Dto.UserDTO;
import com.example.sproject.Models.User;
import com.example.sproject.Services.RegistrationService;
import com.example.sproject.Dto.AuthenticationDTO;
import com.example.sproject.util.JwtUtil;
import com.sun.xml.bind.v2.schemagen.episode.SchemaBindings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final JwtUtil jwtUtil;
   private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    @Autowired
    public AuthController(RegistrationService registrationService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid UserDTO userDto,
                                      BindingResult bindingResult) {
        User user = convertToUser(userDto);
        if (bindingResult.hasErrors()) {
            return Map.of("message", "Ошибка!");
        }
        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);
    }
    public User convertToUser(UserDTO userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

}
