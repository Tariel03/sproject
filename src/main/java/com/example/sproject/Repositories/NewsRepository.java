package com.example.sproject.Repositories;

import com.example.sproject.Models.News;
import com.example.sproject.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findNewsByUser(User user);
    List<News>findNewsByGenre(String genre);
    Optional<News> findNewsByUserAndId(User user, Long id);
    List<News> findNewsByUserAndLiked(User user, Boolean condition);
}
