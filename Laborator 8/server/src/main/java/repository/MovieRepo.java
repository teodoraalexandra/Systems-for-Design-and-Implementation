package repository;

import java.util.List;
import java.util.Optional;

import domain.Movie;

public interface MovieRepo {
    List<Movie> findAllMovies();
    void save(Movie Movie);
    void update(Movie Movie);
    void delete(Long id);
    Optional<Movie> findOne(Long id);
}