package lab10.web.converter;

import lab10.core.model.Client;
import org.springframework.stereotype.Component;

import lab10.web.dto.ClientDto;

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
