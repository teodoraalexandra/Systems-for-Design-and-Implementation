package lab12.web.converter;

import lab12.core.model.BaseEntity;
import lab12.web.dto.BaseDto;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

