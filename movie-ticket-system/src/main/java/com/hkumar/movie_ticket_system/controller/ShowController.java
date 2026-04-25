package com.hkumar.movie_ticket_system.controller;

import com.hkumar.movie_ticket_system.entity.Show;
import com.hkumar.movie_ticket_system.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<Show> addShow(
            @RequestParam Long movieId,
            @RequestParam Long theatreId,
            @RequestBody Show show) {
        return ResponseEntity.ok(showService.addShow(movieId, theatreId, show));
    }

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Show>> getShowsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<Show>> getShowsByTheatre(@PathVariable Long theatreId) {
        return ResponseEntity.ok(showService.getShowsByTheatre(theatreId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Show>> getShowsByMovieAndTheatre(
            @RequestParam Long movieId,
            @RequestParam Long theatreId) {
        return ResponseEntity.ok(showService.getShowsByMovieAndTheatre(movieId, theatreId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.ok("Show deleted successfully");
    }
}
