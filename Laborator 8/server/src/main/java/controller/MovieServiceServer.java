package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.*;
import repository.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceServer implements MovieService {

    @Autowired
    private MovieRepo movieRepo;

    @Override
    public List<Movie> findAllMovies() {
        List<Movie> result = movieRepo.findAllMovies();
        System.out.println("MovieServiceServer: findAllMovies() = " + result);

        return result;
    }

    @Override
    public Movie findMovie(Long id) {
        Movie movie = movieRepo.findOne(id).get();
        System.out.println("MovieServiceServer: findMovie = " + movie);

        return movie;
    }

    @Override
    public void addMovie(Movie movie) {
        movieRepo.save(movie);

        System.out.println("Add successfully! -- MovieServiceServer");
    }

    @Override
    @Transactional
    public void updateMovie(Movie movie) {
        Optional<Movie> movieOptional = movieRepo.findOne(movie.getId());

        if (movieOptional.isPresent()) {
            movieRepo.update(movie);
        }

        System.out.println("Update successfully: MovieServiceServer");
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepo.delete(id);

        System.out.println("Delete successfully: MovieServiceServer");
    }

}