package lab12.web.controller;

import lab12.core.model.Client;
import lab12.core.service.ClientServiceImpl;
import lab12.web.converter.ClientConverter;
import lab12.web.dto.ClientDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ClientController {
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public List<ClientDto> getClients() {
        log.trace("ClientController - getClients - enter method");

        List<Client> clients = (List<Client>) clientService.getAllEntities();

        //List<Client> clients = (List<Client>) clientService.getJPQL();

        return new ArrayList<>(clientConverter.convertModelsToDtos(clients));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto saveClient(@RequestBody ClientDto clientDto) {
        log.trace("ClientController - saveClient - enter method");
        return clientConverter.convertModelToDto(clientService.addEntity(
                clientConverter.convertDtoToModel(clientDto)
        ));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id,
                         @RequestBody ClientDto clientDto) {
        log.trace("ClientController - updateClient - enter method");
        return clientConverter.convertModelToDto( clientService.updateEntity(id,
                clientConverter.convertDtoToModel(clientDto)));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        log.trace("ClientController - deleteClient - enter method");

        clientService.deleteEntity(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
