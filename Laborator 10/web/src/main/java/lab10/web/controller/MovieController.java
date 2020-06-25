package lab10.web.controller;

import lab10.core.service.MovieService;
import lab10.web.converter.MovieConverter;
import lab10.web.dto.MovieDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lab10.web.dto.MoviesDto;


@RestController
public class MovieController {
    public static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;


    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    MoviesDto getMovies() {
        log.trace("MovieController - getMovies - enter method");
        return new MoviesDto(movieConverter
                .convertModelsToDtos(movieService.getAllMovies()));
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    MovieDto saveMovie(@RequestBody MovieDto movieDto) {
        log.trace("MovieController - saveMovie - enter method");
        return movieConverter.convertModelToDto(movieService.saveMovie(
                movieConverter.convertDtoToModel(movieDto)
        ));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    MovieDto updateMovie(@PathVariable Long id,
                         @RequestBody MovieDto movieDto) {
        log.trace("MovieController - updateMovie - enter method");
        return movieConverter.convertModelToDto( movieService.updateMovie(id,
                movieConverter.convertDtoToModel(movieDto)));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteMovie(@PathVariable Long id){
        log.trace("MovieController - deleteMovie - enter method");

        movieService.deleteMovieById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
