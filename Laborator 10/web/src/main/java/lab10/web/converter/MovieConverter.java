package lab10.web.converter;

import lab10.core.model.Movie;
import org.springframework.stereotype.Component;

import lab10.web.dto.MovieDto;

@Component
public class MovieConverter extends BaseConverter<Movie, MovieDto> {
    @Override
    public Movie convertDtoToModel(MovieDto dto) {
        Movie movie = Movie.builder()
                .serialNumber(dto.getSerialNumber())
                .title(dto.getTitle())
                .director(dto.getDirector())
                .duration(dto.getDuration())
                .build();
        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto dto = MovieDto.builder()
                .serialNumber(movie.getSerialNumber())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .duration(movie.getDuration())
                .build();
        dto.setId(movie.getId());
        return dto;
    }
}

