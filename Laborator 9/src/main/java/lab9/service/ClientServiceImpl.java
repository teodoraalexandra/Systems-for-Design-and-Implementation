package lab9.service;

import lab9.model.Client;
import lab9.validators.Validator;
import lab9.validators.ValidatorException;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lab9.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        //return clientRepository.findAll();

        //NEW SORT IMPLEMENTATION
        Sort sort = new Sort("name");
        return clientRepository.findAll(sort);
    }

    @Override
    public void saveClient(Client client) {
        Optional<Client> findClient = clientRepository.findById(client.getId());
        if (findClient.isPresent()) {
            throw new ValidatorException("Id is already taken");
        }

        try {
            log.trace("saveClient - method entered: client={}", client);
            clientRepository.save(client);
            log.trace("saveClient - method finished");
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateClient(Client client) {
        Optional<Client> findClient = clientRepository.findById(client.getId());
        if (findClient.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        try {
            log.trace("updateClient - method entered: client={}", client);
            clientRepository.findById(client.getId())
                    .ifPresent(c -> {
                        c.setSerialNumber(client.getSerialNumber());
                        c.setName(client.getName());
                        log.debug("updateClient - updated: client={}", c);
                    });
            log.trace("updateClient - method finished");
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

        log.trace("deleteClient - method entered: id={}", id);
        clientRepository.deleteById(id);
        log.trace("deleteClient - method finished");
    }
}
