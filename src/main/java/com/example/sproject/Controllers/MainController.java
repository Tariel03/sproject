package com.example.sproject.Controllers;


import com.example.sproject.Exceptions.NewsException;
import com.example.sproject.Models.*;
import com.example.sproject.Repositories.DollRepository;
import com.example.sproject.Services.*;
import com.example.sproject.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
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
    };
    @GetMapping("/user/{id}")
    public String users(@PathVariable("id")Long id){
        User user = new User();
        Optional<User> optionalUser = clientService.findById(id);
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }
        return user.getLastname()+" "+ user.getFirstname();
    }
    @GetMapping("/news/myNews")
    public List<News> newsByClient(){
        return newsService.userById(registrationService.currentUser());
    }
    @GetMapping("/news")
    public List<News> AllNews(){
        return newsService.news();
    };
    @GetMapping("/comments")
    public List<Comment> AllComments(){
        return commentService.comments();
    };

    @GetMapping("/news/{genre}")
    public List<News> newsByGenre(@PathVariable(value = "genre") String genre){
        System.out.println(newsService.newsByGenre(genre));

        return newsService.newsByGenre(genre);
    }
    @PostMapping("/doll")
    public ResponseEntity<HttpStatus> createAccount(@RequestBody Doll doll){
        registrationService.doll(doll);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/write/news")
    public ResponseEntity<HttpStatus>writeNews(@RequestBody @Valid News news, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error: fieldErrors
                 ) {
                message.append(error.getField()).append("-").append(error.getDefaultMessage());
            }
            throw new NewsException(message.toString());
        }
        newsService.writeNews(news);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/write/comment/{id}")
    public ResponseEntity<HttpStatus>writeComment(@RequestBody @Valid Comment comment, BindingResult bindingResult, @PathVariable("id") Long id){
        if (bindingResult.hasErrors()){
            StringBuilder message = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error: fieldErrors
            ) {
                message.append(error.getField()).append("-").append(error.getDefaultMessage());
            }
            throw new NewsException(message.toString());
        }
        comment.setNews(newsService.newsById(id));
        commentService.writeComment(comment);
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

    @PutMapping("/news/upload/photo/{id}")
    public ResponseEntity<ImageUploadResponse> uploadNewsPhoto(@RequestParam("image") MultipartFile file, @PathVariable("id") Long id)
            throws IOException {
        News news = newsService.newsById(id);
        news.setPhoto(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType()).
                image(ImageUtility.compressImage(file.getBytes())).build().getImage());
        newsService.save(news);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }

    @DeleteMapping("/delete/photo")
    public ResponseEntity<ImageUploadResponse> deletePhoto(){
        User user = registrationService.currentUser();
        clientService.deletePhoto(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image deleted successfully: " ));
    }

    @DeleteMapping("/news/delete/photo/{id}")
    public ResponseEntity<ImageUploadResponse> deleteNewsPhoto(@PathVariable("id")Long id){
        News news = newsService.newsById(id);
        newsService.deletePhoto(news);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image deleted successfully: " ));
    }

    @PutMapping("edit/lastname/{lastname}")
    public ResponseEntity<HttpStatus> editLastname(@PathVariable("lastname")String lastname){
        User user = registrationService.currentUser();
        user.setLastname(lastname);
        clientService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("edit/firstname/{firstname}")
    public ResponseEntity<HttpStatus> editFirstname(@PathVariable("firstname")String firstname){
        User user = registrationService.currentUser();
        user.setFirstname(firstname);
        clientService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("edit/username/{username}")
    public ResponseEntity<HttpStatus> editUsername(@PathVariable("username")String username){
        User user = registrationService.currentUser();
        user.setUsername(username);
        clientService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("news/delete/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable("id")Long id){
        newsService.delete(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
    @PutMapping("news/like/{id}")
    public ResponseEntity<HttpStatus> makeFavourite(@PathVariable("id")Long id){
        newsService.makeFavourite(newsService.newsById(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
    @PutMapping("news/remove/like/{id}")
    public ResponseEntity<HttpStatus> removeFavourite(@PathVariable("id")Long id){
        newsService.removeFavourite(newsService.newsById(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
    @GetMapping("news/favourite")
    public List<News> favouriteNews(){
        return newsService.favouriteNews();
    }















}
