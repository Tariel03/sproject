package com.example.sproject.Controllers;

import com.example.sproject.Exceptions.NewsException;
import com.example.sproject.Models.Image;
import com.example.sproject.Models.ImageUploadResponse;
import com.example.sproject.Models.News;
import com.example.sproject.Services.NewsService;
import com.example.sproject.Services.RegistrationService;
import com.example.sproject.util.ImageUtility;
import lombok.AllArgsConstructor;
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

@RestController
@RequestMapping("/news")
//TODO
public class NewsController {
    NewsService newsService;
    RegistrationService registrationService;
    @Autowired
    public NewsController(NewsService newsService, RegistrationService registrationService) {
        this.newsService = newsService;
        this.registrationService = registrationService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable("id") Long id) {
        newsService.delete(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<HttpStatus> makeFavourite(@PathVariable("id") Long id) {
        newsService.makeFavourite(newsService.newsById(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PutMapping("remove/like/{id}")
    public ResponseEntity<HttpStatus> removeFavourite(@PathVariable("id") Long id) {
        newsService.removeFavourite(newsService.newsById(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/favourite")
    public List<News> favouriteNews() {
        return newsService.favouriteNews();
    }

    @DeleteMapping("/delete/photo/{id}")
    public ResponseEntity<ImageUploadResponse> deleteNewsPhoto(@PathVariable("id") Long id) {
        News news = newsService.newsById(id);
        newsService.deletePhoto(news);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image deleted successfully: "));
    }

    @PutMapping("/upload/photo/{id}")
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
    @PostMapping("/write")
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
    @GetMapping("/myNews")
    public List<News> newsByClient(){
        return newsService.userById(registrationService.currentUser());
    }
    @GetMapping
    public List<News> AllNews(){
        return newsService.news();
    };
    @GetMapping("/{genre}")
    public List<News> newsByGenre(@PathVariable(value = "genre") String genre){
        return newsService.newsByGenre(genre);
    }
}
