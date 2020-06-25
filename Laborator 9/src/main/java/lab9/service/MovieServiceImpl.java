package lab9.service;

import lab9.model.Movie;
import lab9.validators.Validator;
import lab9.validators.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lab9.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        //OLD LAB IMPLEMENTATION
        return movieRepository.findAll();

        //NEW SORT IMPLEMENTATION
        //Sort sort = new Sort("title");
        //return movieRepository.findAll(sort);
    }

    @Override
    public void saveMovie(Movie movie) {
        Optional<Movie> findMovie = movieRepository.findById(movie.getId());
        if (findMovie.isPresent()) {
            throw new ValidatorException("Id is already taken");
        }

        try {
            log.trace("saveMovie - method entered: movie={}", movie);
            movieRepository.save(movie);
            log.trace("saveMovie - method finished");
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateMovie(Movie movie) {
        Optional<Movie> findMovie = movieRepository.findById(movie.getId());
        if (findMovie.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        try {
            log.trace("updateMovie - method entered: movie={}", movie);
            movieRepository.findById(movie.getId())
                    .ifPresent(m -> {
                        m.setSerialNumber(movie.getSerialNumber());
                        m.setTitle(movie.getTitle());
                        m.setDirector(movie.getDirector());
                        m.setDuration(movie.getDuration());
                        log.debug("updateMovie - updated: movie={}", m);
                    });
            log.trace("updateMovie - method finished");
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

        log.trace("deleteMovie - method entered: id={}", id);
        movieRepository.deleteById(id);
        log.trace("deleteMovie - method finished");
    }
}
