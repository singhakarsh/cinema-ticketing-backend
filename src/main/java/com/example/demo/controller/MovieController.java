package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/movies") // This sets the base URL path for this controller
public class MovieController {

    private final MovieRepository movieRepository;

    // Spring automatically gives us our Movie repository here
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // This endpoint will return all the movies in our database as clean JSON data
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}