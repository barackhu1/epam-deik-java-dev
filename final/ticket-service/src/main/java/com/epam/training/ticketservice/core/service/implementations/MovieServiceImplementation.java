package com.epam.training.ticketservice.core.service.implementations;

import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.repository.MovieRepository;
import com.epam.training.ticketservice.core.service.interfaces.MovieServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImplementation implements MovieServiceInterface {
    private final MovieRepository movieRepository;
    @Override
    public String movieCreate(String movieName, String movieGenre, int watchTime) {
        if (movieRepository.findBymovieName(movieName).isPresent()){
            return "The movie is already existed.";
        }else {
            Movie movie = new Movie(movieName, movieGenre, watchTime);
            movieRepository.save(movie);
            return "The movie created successfully.";
        }
    }

    @Override
    public String movieUpdate(String movieName, String movieGenre, int watchTime) {
        if (movieRepository.findBymovieName(movieName).isPresent()){
            Movie movie = movieRepository.findBymovieName(movieName).get();
            movie.setMovieGenre(movieGenre);
            movie.setWatchTime(watchTime);
            movieRepository.save(movie);
            return "The movie was updated successfully.";
        }else {
            return "The movie does not exists.";
        }
    }

    @Override
    public String movieDelete(String movieName) {
        if (movieRepository.findBymovieName(movieName).isPresent()){
            Movie movie = movieRepository.findBymovieName(movieName).get();
            movieRepository.delete(movie);
            return "The movie was deleted successfully.";
        }else {
            return "The movie does not exists.";
        }
    }

    @Override
    public List<Movie> movieList() {
        return  movieRepository.findAll();
    }
}
