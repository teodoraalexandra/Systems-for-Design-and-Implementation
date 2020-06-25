package lab10.web.controller;

import lab10.core.service.ClientService;
import lab10.web.converter.ClientConverter;
import lab10.web.dto.ClientDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lab10.web.dto.ClientsDto;


@RestController
public class ClientController {
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;


    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientsDto getClients() {
        log.trace("ClientController - getClients - enter method");
        return new ClientsDto(clientConverter
                .convertModelsToDtos(clientService.getAllClients()));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto saveClient(@RequestBody ClientDto clientDto) {
        log.trace("ClientController - saveClient - enter method");
        return clientConverter.convertModelToDto(clientService.saveClient(
                clientConverter.convertDtoToModel(clientDto)
        ));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id,
                           @RequestBody ClientDto clientDto) {
        log.trace("ClientController - updateClient - enter method");
        return clientConverter.convertModelToDto( clientService.updateClient(id,
                clientConverter.convertDtoToModel(clientDto)));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        log.trace("ClientController - deleteClient - enter method");

        clientService.deleteClientById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
