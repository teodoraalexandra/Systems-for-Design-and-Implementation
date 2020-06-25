package lab11.web.converter;

import lab11.core.model.Rental;
import lab11.web.dto.RentalDto;
import org.springframework.stereotype.Component;

@Component
public class RentalConverter extends BaseConverter<Rental, RentalDto> {
    @Override
    public Rental convertDtoToModel(RentalDto dto) {
        Rental rental = Rental.builder()
                .clientId(dto.getClientId())
                .movieId(dto.getMovieId())
                .build();
        rental.setId(dto.getId());
        return rental;
    }

    @Override
    public RentalDto convertModelToDto(Rental rental) {
        RentalDto dto = RentalDto.builder()
                .clientId(rental.getClientId())
                .movieId(rental.getMovieId())
                .build();
        dto.setId(rental.getId());
        return dto;
    }
}
