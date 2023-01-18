package com.example.sproject.Controllers;

import com.example.sproject.Exceptions.NewsException;
import com.example.sproject.Models.Image;
import com.example.sproject.Models.ImageUploadResponse;
import com.example.sproject.Models.News;
import com.example.sproject.Models.User;
import com.example.sproject.Services.NewsService;
import com.example.sproject.Services.RegistrationService;
import com.example.sproject.util.ImageUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jdk.jfr.Description;
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
public class NewsController {
    NewsService newsService;
    RegistrationService registrationService;
    @Autowired
    public NewsController(NewsService newsService, RegistrationService registrationService) {
        this.newsService = newsService;
        this.registrationService = registrationService;
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Remove news", description = "This request deletes news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable("id") Long id) {
        newsService.delete(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PutMapping("/like/{id}")
    @Operation(summary = "Like", description = "This request sets a like for certain news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
    public ResponseEntity<HttpStatus> makeFavourite(@PathVariable("id") Long id) {
        newsService.makeFavourite(newsService.newsById(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PutMapping("remove/like/{id}")
    @Operation(summary = "Remove like", description = "This request deletes a like of certain news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
    public ResponseEntity<HttpStatus> removeFavourite(@PathVariable("id") Long id) {
        newsService.removeFavourite(newsService.newsById(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/favourite")
    @Operation(summary = "Favourite  news", description = "This request shows favourite news of a current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
    public List<News> favouriteNews() {
        return newsService.favouriteNews();
    }

    @DeleteMapping("/delete/photo/{id}")
    @Operation(summary = "Delete photo of a news", description = "This request deletes picture of  news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
    public ResponseEntity<ImageUploadResponse> deleteNewsPhoto(@PathVariable("id") Long id) {
        News news = newsService.newsById(id);
        newsService.deletePhoto(news);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image deleted successfully: "));
    }

    @PutMapping("/upload/photo/{id}")
    @Operation(summary = "Upload photo to  news", description = "This request sets picture for  news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
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
    @Operation(summary = "Writing  news", description = "This request writes new  news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
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
    @Operation(summary = "My news", description = "This request shows news of a current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
    public List<News> newsByClient(){
        return newsService.userById(registrationService.currentUser());
    }
    @GetMapping
    @Operation(summary = "All news", description = "This request shows all news")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
    public List<News> AllNews(){
        return newsService.news();
    };
    @GetMapping("/{genre}")
    @Operation(summary = "Sort by genre", description = "This request shows news of by a certain genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
    public List<News> newsByGenre(@PathVariable(value = "genre") String genre){
        return newsService.newsByGenre(genre);
    }

    @GetMapping("/search/{keyword}")
    @Operation(summary = "Find news by a keyword", description = "This request searches news by a keyword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = News.class)))) })
    public List<News> newsSearch(@PathVariable(value = "keyword") String keyword){
        return newsService.findByKeyword(keyword);
    }



}
