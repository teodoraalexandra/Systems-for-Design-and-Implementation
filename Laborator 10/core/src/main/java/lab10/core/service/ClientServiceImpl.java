package lab10.core.service;

import lab10.core.model.Client;
import lab10.core.repository.ClientRepository;
import lab10.core.validators.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        //OLD LAB IMPLEMENTATION
        return clientRepository.findAll();

        //NEW SORT IMPLEMENTATION
        //Sort sort = new Sort("name");
        //return clientRepository.findAll(sort);
    }

    @Override
    public Client saveClient(Client client) {
        log.trace("ClientServiceImpl - saveClient - method entered: client={}", client);
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client updateClient(Long id, Client client) {
        Optional<Client> findClient = clientRepository.findById(client.getId());
        if (findClient.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        try {
            log.trace("ClientServiceImpl - updateClient - method entered: client={}", client);

            Client update = clientRepository.findById(id).orElse(client);
            update.setSerialNumber(client.getSerialNumber());
            update.setName(client.getName());

            return update;
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        }
    }

    @Override
    public void deleteClientById(Long id) {
        Optional<Client> findClient = clientRepository.findById(id);
        if (findClient.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        log.trace("ClientServiceImpl - deleteClient - method entered: id={}", id);
        clientRepository.deleteById(id);
        log.trace("ClientServiceImpl - deleteClient - method finished");
    }
}
