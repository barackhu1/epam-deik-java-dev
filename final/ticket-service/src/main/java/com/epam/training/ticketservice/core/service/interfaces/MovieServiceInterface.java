package com.epam.training.ticketservice.core.service.interfaces;

import com.epam.training.ticketservice.core.model.Movie;

import java.util.List;

public interface MovieServiceInterface {
    String movieCreate(String movieName, String movieGenre, int watchTime);
    String movieUpdate(String movieName, String movieGenre, int watchTime);
    String movieDelete(String movieName);
    List<Movie> movieList();
}
