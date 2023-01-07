package com.example.sproject.Services;

import com.example.sproject.Models.News;
import com.example.sproject.Models.User;
import com.example.sproject.Repositories.ClientRepository;
import com.example.sproject.Repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class NewsService {
    NewsRepository newsRepository;
    ClientRepository clientRepository;
    RegistrationService registrationService;
    @Autowired
    public NewsService(NewsRepository newsRepository, ClientRepository clientRepository, RegistrationService registrationService) {
        this.newsRepository = newsRepository;
        this.clientRepository = clientRepository;
        this.registrationService = registrationService;
    }

    public List<News> userById(User user){
        return newsRepository.findNewsByUser(user);
    };
    public News newsById(Long id){
        return newsRepository.findById(id).orElse(null);
    }

    public News newsByUserAndId(User user,Long id){
        return newsRepository.findNewsByUserAndId(user,id).orElse(null);
    }

    public List<News> news(){
        return newsRepository.findAll();
    };
    public List<News> newsByGenre(String genre){
        return newsRepository.findNewsByGenre(genre);
    }

    public void writeNews(News news){
        news.setUser(registrationService.currentUser());
        news.setLocalDate(LocalDate.now());
        newsRepository.save(news);
    }
    public void save(News news){
        newsRepository.save(news);
    }

    public void deletePhoto(News news){
        news.setPhoto(null);
        newsRepository.save(news);
    }

    public void delete(Long id){
        newsRepository.deleteById(id);
    }

    public void makeFavourite(News news){
        news.setLiked(true);
    }
    public void removeFavourite(News news){
        news.setLiked(false);
    }
    public List<News> favouriteNews(){
        return newsRepository.findNewsByUserAndLiked(registrationService.currentUser(), true);
    }







}
