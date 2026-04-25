package com.hkumar.movie_ticket_system.service;

import com.hkumar.movie_ticket_system.entity.Movie;
import com.hkumar.movie_ticket_system.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    public List<Movie> getMoviesByLanguage(String language) {
        return movieRepository.findByLanguage(language);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public Movie updateMovie(Long id, Movie updated) {
        Movie existing = getMovieById(id);
        existing.setName(updated.getName());
        existing.setLanguage(updated.getLanguage());
        existing.setGenre(updated.getGenre());
        existing.setDurationMinutes(updated.getDurationMinutes());
        return movieRepository.save(existing);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
