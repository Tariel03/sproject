package com.example.sproject.Controllers;


import com.example.sproject.Models.Image;
import com.example.sproject.Models.ImageUploadResponse;
import com.example.sproject.Models.User;
import com.example.sproject.Services.*;
import com.example.sproject.util.ImageUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class MainController {
    ClientService clientService;
    NewsService newsService;
    RegistrationService registrationService;
    CommentService commentService;

    @Autowired
    public MainController(ClientService clientService, NewsService newsService, RegistrationService registrationService, CommentService commentService) {
        this.clientService = clientService;
        this.newsService = newsService;
        this.registrationService = registrationService;
        this.commentService = commentService;
    }

    @GetMapping("/all")
    @Operation(summary = "All users", description = "This request shows all users in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public List<User> clientList() {
        return clientService.findAll();
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "User id", description = "This request shows userInfo by User's id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public String users(@PathVariable("id") Long id) {
        User user = new User();
        Optional<User> optionalUser = clientService.findById(id);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user.getLastname() + " " + user.getFirstname();
    }

    @GetMapping("/showUserInfo")
    @ResponseBody
    @Operation(summary = "User info", description = "This request shows userInfo of current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public User showUserInfo() {
        return registrationService.currentUser();
    }

    @PutMapping("/upload/photo")
    @Operation(summary = "Upload photo", description = "This request sets profile picture to the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public ResponseEntity<ImageUploadResponse> uploadPhoto(@RequestParam("image") MultipartFile file)
            throws IOException {
        User user = registrationService.currentUser();
        user.setData(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType()).
                image(ImageUtility.compressImage(file.getBytes())).build().getImage());
        clientService.save(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }

    @DeleteMapping("/delete/photo")
    @Operation(summary = "Delete photo", description = "This request deletes profile picture to the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public ResponseEntity<ImageUploadResponse> deletePhoto() {
        User user = registrationService.currentUser();
        clientService.deletePhoto(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image deleted successfully: "));
    }

    @PutMapping("edit/lastname/{lastname}")
    @Operation(summary = "Edit lastname", description = "This request edits lastname of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public ResponseEntity<HttpStatus> editLastname(@PathVariable("lastname") String lastname) {
        User user = registrationService.currentUser();
        user.setLastname(lastname);
        clientService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/edit/firstname/{firstname}")
    @Operation(summary = "Edit firstname", description = "This request edits firstname of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public ResponseEntity<HttpStatus> editFirstname(@PathVariable("firstname") String firstname) {
        User user = registrationService.currentUser();
        user.setFirstname(firstname);
        clientService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/edit/username/{username}")
    @Operation(summary = "Edit username", description = "This request edits username of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))})
    public ResponseEntity<HttpStatus> editUsername(@PathVariable("username") String username) {
        User user = registrationService.currentUser();
        user.setUsername(username);
        clientService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
