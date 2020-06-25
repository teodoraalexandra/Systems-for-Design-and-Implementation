package service;

import org.springframework.beans.factory.annotation.Autowired;

import domain.*;
import java.util.List;
import java.util.stream.Collectors;

public class MovieServiceClient implements MovieService {

    @Autowired
    MovieService MovieService;

    @Override
    public Movie findMovie(Long id)  {
        System.out.println("Client ---> call method findMovie(" + id + ")\n");
        return MovieService.findMovie(id);
    }

    @Override
    public void addMovie(Movie Movie)  {
        System.out.println("Client ---> call method addMovie(" + Movie + ")\n");
        MovieService.addMovie(Movie);
    }

    @Override
    public void updateMovie(Movie Movie)  {
        System.out.println("Client ---> call method updateMovie(" + Movie + ")\n");
        MovieService.updateMovie(Movie);
    }

    @Override
    public void deleteMovie(Long id)  {
        System.out.println("Client ---> call method deleteMovie(" + id + ")\n");
        MovieService.deleteMovie(id);
    }

    @Override
    public List<Movie> findAllMovies()  {
        System.out.println("Client ---> call method findAllMovies()\n");
        return MovieService.findAllMovies();
    }
}