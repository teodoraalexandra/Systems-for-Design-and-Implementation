package lab11.core.service;

import lab11.core.model.Movie;
import lab11.core.repository.MovieRepository;
import lab11.core.validators.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private static final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        log.trace("MovieServiceImpl - getAllMovies - method entered");
        return movieRepository.findAll();
    }

    @Override
    public Movie saveMovie(Movie movie) {
        log.trace("MovieServiceImpl - saveMovie - method entered: movie={}", movie);
        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public Movie updateMovie(Long id, Movie movie) {
        Optional<Movie> findMovie = movieRepository.findById(movie.getId());
        if (findMovie.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        try {
            log.trace("MovieServiceImpl - updateMovie - method entered: movie={}", movie);

            Movie update = movieRepository.findById(id).orElse(movie);
            update.setSerialNumber(movie.getSerialNumber());
            update.setTitle(movie.getTitle());
            update.setDirector(movie.getDirector());
            update.setDuration(movie.getDuration());

            return update;
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        }
    }

    @Override
    public void deleteMovieById(Long id) {
        Optional<Movie> findMovie = movieRepository.findById(id);
        if (findMovie.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        log.trace("MovieServiceImpl - deleteMovie - method entered: id={}", id);
        movieRepository.deleteById(id);
        log.trace("MovieServiceImpl - deleteMovie - method finished");
    }
}
