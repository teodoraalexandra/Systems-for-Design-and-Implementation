package lab12.web.converter;

import lab12.core.model.Movie;
import lab12.web.dto.MovieDto;
import org.springframework.stereotype.Component;

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

