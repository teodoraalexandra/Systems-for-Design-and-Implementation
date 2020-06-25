package lab11.core.service;

import lab11.core.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();

    Movie saveMovie(Movie movie);

    Movie updateMovie(Long id, Movie movie);

    void deleteMovieById(Long id);
}
