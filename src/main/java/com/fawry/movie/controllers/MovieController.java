package com.fawry.movie.controllers;

import com.fawry.movie.entities.Movie;
import com.fawry.movie.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // ✅ 1. Get all movies
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();


        return ResponseEntity.ok(movies);
    }

    // ✅ 2. Get movie by ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        return movie.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ 3. Add new movie
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.addMovie(movie);
        return ResponseEntity.ok(savedMovie);
    }

    // ✅ 4. Update movie by ID
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id,
                                             @RequestBody Movie updatedMovie) {
        Optional<Movie> movie = movieService.updateMovie(id, updatedMovie);
        return movie.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // ✅ 5. Delete movie by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        boolean deleted = movieService.deleteMovie(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 لو اتمسح
        } else {
            return ResponseEntity.notFound().build();  // 404 لو مش موجود
        }
    }



}
