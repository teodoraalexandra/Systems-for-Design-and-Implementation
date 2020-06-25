package lab12.web.converter;

import lab12.core.model.Client;
import lab12.web.dto.ClientDto;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDto> {
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        Client client = Client.builder()
                .serialNumber(dto.getSerialNumber())
                .name(dto.getName())
                .build();
        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = ClientDto.builder()
                .serialNumber(client.getSerialNumber())
                .name(client.getName())
                .build();
        dto.setId(client.getId());
        return dto;
    }
}
