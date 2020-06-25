package lab12.web.controller;

import lab12.core.model.Movie;
import lab12.core.service.MovieServiceImpl;
import lab12.web.converter.MovieConverter;
import lab12.web.dto.MovieDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class MovieController {
    public static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private MovieConverter movieConverter;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List<MovieDto> getMovies() {
        log.trace("MovieController - getMovies - enter method");

        List<Movie> movies = (List<Movie>) movieService.getAllEntities();

        return new ArrayList<>(movieConverter.convertModelsToDtos(movies));
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    MovieDto saveMovie(@RequestBody MovieDto movieDto) {
        log.trace("MovieController - saveMovie - enter method");
        return movieConverter.convertModelToDto(movieService.addEntity(
                movieConverter.convertDtoToModel(movieDto)
        ));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    MovieDto updateMovie(@PathVariable Long id,
                         @RequestBody MovieDto movieDto) {
        log.trace("MovieController - updateMovie - enter method");
        return movieConverter.convertModelToDto( movieService.updateEntity(id,
                movieConverter.convertDtoToModel(movieDto)));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteMovie(@PathVariable Long id){
        log.trace("MovieController - deleteMovie - enter method");

        movieService.deleteEntity(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
