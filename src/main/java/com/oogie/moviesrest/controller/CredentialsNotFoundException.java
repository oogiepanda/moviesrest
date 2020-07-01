package com.oogie.moviesrest.controller;

public class CredentialsNotFoundException extends RuntimeException{
    CredentialsNotFoundException(Integer id) {
       super("Could not find credentials " + id);
    }
}
