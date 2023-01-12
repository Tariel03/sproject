package com.example.sproject.Controllers;


import com.example.sproject.Models.Doll;
import com.example.sproject.Models.Image;
import com.example.sproject.Models.ImageUploadResponse;
import com.example.sproject.Models.User;
import com.example.sproject.Repositories.DollRepository;
import com.example.sproject.Services.*;
import com.example.sproject.util.ImageUtility;
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
    DollRepository dollRepository;
    CommentService commentService;
    FileServiceImpl fileService;

    @Autowired
    public MainController(ClientService clientService, NewsService newsService, RegistrationService registrationService, DollRepository dollRepository, CommentService commentService, FileServiceImpl fileService) {
        this.clientService = clientService;
        this.newsService = newsService;
        this.registrationService = registrationService;
        this.dollRepository = dollRepository;
        this.commentService = commentService;
        this.fileService = fileService;
    }
    @GetMapping("/all")
    public List<User> clientList() {
        return clientService.findAll();
    }
    @GetMapping("/user/{id}")
    public String users(@PathVariable("id") Long id) {
        User user = new User();
        Optional<User> optionalUser = clientService.findById(id);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user.getLastname() + " " + user.getFirstname();
    }
    @PostMapping("/doll")
    public ResponseEntity<HttpStatus> createAccount(@RequestBody Doll doll) {
        registrationService.doll(doll);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/showUserInfo")
    @ResponseBody
    public User showUserInfo() {
        return registrationService.currentUser();
    }

    @PutMapping("/upload/photo")
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
    public ResponseEntity<ImageUploadResponse> deletePhoto() {
        User user = registrationService.currentUser();
        clientService.deletePhoto(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image deleted successfully: "));
    }
    @PutMapping("edit/lastname/{lastname}")
    public ResponseEntity<HttpStatus> editLastname(@PathVariable("lastname") String lastname) {
        User user = registrationService.currentUser();
        user.setLastname(lastname);
        clientService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/edit/firstname/{firstname}")
    public ResponseEntity<HttpStatus> editFirstname(@PathVariable("firstname") String firstname) {
        User user = registrationService.currentUser();
        user.setFirstname(firstname);
        clientService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/edit/username/{username}")
    public ResponseEntity<HttpStatus> editUsername(@PathVariable("username") String username) {
        User user = registrationService.currentUser();
        user.setUsername(username);
        clientService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping(path = "/get/photo")
    public ResponseEntity<byte[]> getImage() throws IOException {
        final User user = registrationService.currentUser();
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(user.getLastname()))
                .body(ImageUtility.decompressImage(user.getData()));
    }



}
