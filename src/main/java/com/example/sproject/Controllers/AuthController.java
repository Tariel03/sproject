package com.example.sproject.Controllers;

import com.example.sproject.Dto.UserDTO;
import com.example.sproject.Models.User;
import com.example.sproject.Repositories.ClientRepository;
import com.example.sproject.Services.RegistrationService;
import com.example.sproject.Dto.AuthenticationDTO;
import com.example.sproject.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final JwtUtil jwtUtil;
   private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;
    private final MessageSource messageSource;
    @Autowired
    public AuthController(RegistrationService registrationService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, ModelMapper modelMapper, ClientRepository clientRepository, MessageSource messageSource) {
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
        this.messageSource = messageSource;
    }

    @PostMapping("/registration")
    @Operation(summary = "Registration", description = "This request creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))) })
    public Map<String, String> performRegistration(@RequestBody @Valid UserDTO userDto,
                                      BindingResult bindingResult) {
        User user = convertToUser(userDto);
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            assert fieldError != null;
            String message = messageSource.getMessage(fieldError,null);
            return Map.of("message",message );
        }
        if(clientRepository.findByUsername(user.getUsername()).isPresent()) {
            return Map.of("User by this username exists",user.getUsername());
        }
        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "This request is used for logging in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))) })
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
