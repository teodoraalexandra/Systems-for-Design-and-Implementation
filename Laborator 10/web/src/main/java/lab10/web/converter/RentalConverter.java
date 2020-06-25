package lab10.web.converter;

import lab10.core.model.Rental;
import org.springframework.stereotype.Component;

import lab10.web.dto.RentalDto;

@Component
public class RentalConverter extends BaseConverter<Rental, RentalDto> {
    @Override
    public Rental convertDtoToModel(RentalDto dto) {
        Rental rental = Rental.builder()
                .cid(dto.getCid())
                .mid(dto.getMid())
                .build();
        rental.setId(dto.getId());
        return rental;
    }

    @Override
    public RentalDto convertModelToDto(Rental rental) {
        RentalDto dto = RentalDto.builder()
                .cid(rental.getCid())
                .mid(rental.getMid())
                .build();
        dto.setId(rental.getId());
        return dto;
    }
}
