package com.oogie.moviesrest.controller;

import com.oogie.moviesrest.MovieListRepository;
import com.oogie.moviesrest.model.ErrorResponse;
import com.oogie.moviesrest.model.MovieListEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieListController {
    private final MovieListRepository repository;

    MovieListController(MovieListRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/movielist")
    List<MovieListEntity> all() {
        return repository.findAll();
    }

    @PostMapping("/movielist")
    MovieListEntity newMovieList(@RequestBody MovieListEntity nuMovies) {
        return repository.save(nuMovies);
    }

    @GetMapping("/movielist/{id}")
    MovieListEntity one(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    @PutMapping("/movielist/{id}")
    MovieListEntity replaceMovieList(@RequestBody MovieListEntity nuMovies, @PathVariable Integer id) {
        return repository.findById(id).map(movies -> {
            movies.setMoviename(nuMovies.getMoviename());
            movies.setDirector(nuMovies.getDirector());
            movies.setYear(nuMovies.getYear());
            movies.setGenre(nuMovies.getGenre());
            return repository.save(movies);
        }).orElseGet(() -> {
            nuMovies.setId(id);
            return repository.save(nuMovies);
        });
    }

    @DeleteMapping("/movielist/{id}")
    ErrorResponse deleteMovieList(@PathVariable Integer id) {
        try {
            repository.deleteById(id);
            return new ErrorResponse();
        } catch (Exception e) {
            String s = formatErrorResponse(e.getMessage(), MovieListEntity.class);
            return new ErrorResponse(-1, s);
        }
    }

    private String formatErrorResponse(String origMessage, Class c) {
        String searchStr = c.getName();
        int startIndex = origMessage.indexOf(searchStr);
        int endIndex = startIndex + searchStr.length();
        String s1 = origMessage.substring(0, startIndex);
        String s2 = origMessage.substring(endIndex + 1);
        StringBuilder sb = new StringBuilder(s1);
        sb.append(s2);
        return sb.toString();
    }
}
