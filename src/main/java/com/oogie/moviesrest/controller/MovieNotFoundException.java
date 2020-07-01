package com.oogie.moviesrest.controller;

public class MovieNotFoundException extends RuntimeException{
    MovieNotFoundException(Integer id) {
        super("Could not find credentials " + id);
    }
}
