package lab12.web.converter;

import lab12.core.model.Client;
import lab12.core.model.Rental;
import lab12.web.dto.ClientDto;
import lab12.web.dto.MovieDto;
import lab12.web.dto.RentalDto;
import org.springframework.stereotype.Component;

@Component
public class RentalConverter extends BaseConverter<Rental, RentalDto> {
    @Override
    public Rental convertDtoToModel(RentalDto dto) {
         Rental rental = Rental.builder()
                .client(dto.getClient())
                .movie(dto.getMovie())
                .build();
        rental.setId(dto.getId());
        return rental;
    }

    @Override
    public RentalDto convertModelToDto(Rental rental) {
        RentalDto dto = RentalDto.builder()
                .client(rental.getClient())
                .movie(rental.getMovie())
                .build();
        dto.setId(rental.getId());
        return dto;
    }
}

