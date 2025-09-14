package com.fawry.movie.services;

import com.fawry.movie.entities.Movie;
import com.fawry.movie.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    // Constructor Injection
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Get all movies
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Get movie by ID
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    // Get movie by IMDb ID
    public Optional<Movie> getMovieByImdbId(String imdbId) {
        return movieRepository.findByImdbId(imdbId);
    }

    // Add a new movie
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // ✅ Update movie in Service
    public Optional<Movie> updateMovie(Long id, Movie updatedMovie) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(updatedMovie.getTitle());
                    movie.setYear(updatedMovie.getYear());
                    movie.setImdbId(updatedMovie.getImdbId());
                    movie.setType(updatedMovie.getType());
                    movie.setPosterUrl(updatedMovie.getPosterUrl());
                    return movieRepository.save(movie);
                });
    }


    // Delete movie
    public boolean deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found with id " + id);
        }
        movieRepository.deleteById(id);
        return false;
    }
}
