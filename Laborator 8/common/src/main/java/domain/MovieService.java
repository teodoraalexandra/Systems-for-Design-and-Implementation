package domain;

import java.io.Serializable;
import java.util.List;

public interface MovieService extends Serializable{

    List<Movie> findAllMovies();
    Movie findMovie(Long id);
    void addMovie(Movie Movie);
    void updateMovie(Movie Movie);
    void deleteMovie(Long id);
}