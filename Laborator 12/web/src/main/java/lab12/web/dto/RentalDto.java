package lab12.web.dto;

import lab12.core.model.Client;
import lab12.core.model.Movie;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class RentalDto extends BaseDto {
    private Client client;
    private Movie movie;
}
