package com.hkumar.movie_ticket_system.service;

import com.hkumar.movie_ticket_system.entity.Movie;
import com.hkumar.movie_ticket_system.entity.Show;
import com.hkumar.movie_ticket_system.entity.Theatre;
import com.hkumar.movie_ticket_system.repository.MovieRepository;
import com.hkumar.movie_ticket_system.repository.ShowRepository;
import com.hkumar.movie_ticket_system.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;

    public Show addShow(Long movieId, Long theatreId, Show show) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found with id: " + theatreId));
        show.setMovie(movie);
        show.setTheatre(theatre);
        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Show getShowById(Long id) {
        return showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + id));
    }

    public List<Show> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public List<Show> getShowsByTheatre(Long theatreId) {
        return showRepository.findByTheatreId(theatreId);
    }

    public List<Show> getShowsByMovieAndTheatre(Long movieId, Long theatreId) {
        return showRepository.findByMovieIdAndTheatreId(movieId, theatreId);
    }

    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }
}
