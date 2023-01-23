package com.example.sproject.Repositories;

import com.example.sproject.Models.News;
import com.example.sproject.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NewsRepositoryTest {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    NewsRepository newsRepository;


    private User getUser() {
        User user = new User("tariel", "nurlanobivc", "Akmatov", "Tariel");
        clientRepository.save(user);
        return user;
    }

    @Test
    void findNewsByUser() {
        User user = getUser();
        clientRepository.save(user);
        News news = new News(1L, "iehasidhsadhsahd", "adsdhsjahdhaks", "asdasdoiashd", "anime",null,false,
                user,null);
        newsRepository.save(news);
        List<News> newsList = newsRepository.findNewsByUser(clientRepository.findByUsername("tariel").get());
        assertNotEquals(newsList.size(), 0);
    }

    @Test
    void findNewsByGenre() {
        User user = getUser();
        clientRepository.save(user);
        News news = new News(1L, "iehasidhsadhsahd", "adsdhsjahdhaks", "asdasdoiashd", "anime",null,false,
                user,null);
        newsRepository.save(news);
        List<News> newsList = newsRepository.findNewsByGenre("anime");
        assertNotEquals(newsList.size(), 0);
    }

    @Test
    void findNewsByUserAndId() {
        User user = getUser();
        clientRepository.save(user);
        News news = new News(1L, "iehasidhsadhsahd", "adsdhsjahdhaks", "asdasdoiashd", "anime",null,false,
                user,null);
        newsRepository.save(news);
        assertNotNull(newsRepository.findNewsByUserAndId(user,news.getId()));
    }

    @Test
    void findNewsByUserAndLiked(){
        User user = getUser();
        clientRepository.save(user);
        News news = new News(1L, "iehasidhsadhsahd", "adsdhsjahdhaks", "asdasdoiashd", "anime",null,true,user,null);
        newsRepository.save(news);
        assertNotEquals(0,newsRepository.findNewsByUserAndLiked(user,true).size());
    }

    @Test
    void findNewsByHeaderStartsWith() {
        User user = getUser();
        clientRepository.save(user);
        News news = new News(1L, "iehasidhsadhsahd", "adsdhsjahdhaks", "asdasdoiashd", "anime",null,true,user,null);
        newsRepository.save(news);
        assertNotEquals(0, newsRepository.findNewsByHeaderStartsWith("ie").size());

    }
}