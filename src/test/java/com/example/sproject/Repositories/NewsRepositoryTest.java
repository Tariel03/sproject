package com.example.sproject.Repositories;

import com.example.sproject.Models.News;
import com.example.sproject.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NewsRepositoryTest {
    @Autowired
    NewsRepository newsRepository;

    @Test
    void findNewsByGenre() {
        assertNotNull(newsRepository.findNewsByGenre("anime"));
    }
    @Test
    void findNewsByHeaderStartsWith(){
        assertNotNull(newsRepository.findNewsByHeaderStartsWith("M"));
    }
    @Test
    void findNewsByName(){
        News news = new News();
        news.setLiked(true);
        news.setUser(null);
        news.setHeader("eqewqe");
        news.setSmall("wkoekw");
        news.setGenre("dd");
        news.setText("2131231");
        news.setId(1L);
        newsRepository.save(news);
        assertEquals(news, newsRepository.findById(1L).get());
    }

}