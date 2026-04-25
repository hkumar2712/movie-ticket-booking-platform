package com.hkumar.movie_ticket_system.service;

import com.hkumar.movie_ticket_system.entity.Theatre;
import com.hkumar.movie_ticket_system.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreService {

    private final TheatreRepository theatreRepository;

    public Theatre addTheatre(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public Theatre getTheatreById(Long id) {
        return theatreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theatre not found with id: " + id));
    }

    public List<Theatre> getTheatresByCity(String city) {
        return theatreRepository.findByCity(city);
    }

    public Theatre updateTheatre(Long id, Theatre updated) {
        Theatre existing = getTheatreById(id);
        existing.setName(updated.getName());
        existing.setCity(updated.getCity());
        existing.setAddress(updated.getAddress());
        existing.setTotalScreens(updated.getTotalScreens());
        return theatreRepository.save(existing);
    }

    public void deleteTheatre(Long id) {
        theatreRepository.deleteById(id);
    }
}
