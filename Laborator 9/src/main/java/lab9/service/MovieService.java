package lab9.service;

import lab9.model.Movie;
import java.util.List;


public interface MovieService {
    List<Movie> getAllMovies();

    void saveMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovieById(Long id);
}