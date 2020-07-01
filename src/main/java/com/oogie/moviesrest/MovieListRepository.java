package com.oogie.moviesrest;

import com.oogie.moviesrest.model.MovieListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieListRepository extends JpaRepository<MovieListEntity, Integer> {
}
