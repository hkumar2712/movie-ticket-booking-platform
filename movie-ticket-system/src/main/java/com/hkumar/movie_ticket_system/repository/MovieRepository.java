package com.hkumar.movie_ticket_system.repository;

import com.hkumar.movie_ticket_system.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByLanguage(String language);
    List<Movie> findByGenre(String genre);
}
