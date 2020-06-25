package socket.server.service;

import org.xml.sax.SAXException;
import socket.common.domain.BaseEntity;
import socket.common.domain.Book;
import socket.common.domain.Client;
import socket.common.domain.Transaction;
import socket.common.domain.validators.Validator;
import socket.common.domain.validators.ValidatorException;
import socket.server.repository.Repository;
import socket.server.repository.Sort;
import socket.server.repository.SortingRepository;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.common.collect.Sets.newHashSet;

public class ClientServiceServer {
    //private Repository<Long, Client> repository;
    private SortingRepository<Long, Client> repository;
    private Validator<Client> clientValidator;

    public ClientServiceServer(SortingRepository<Long, Client> repository, Validator<Client> clientValidator) {
        this.repository = repository;
        this.clientValidator = clientValidator;
    }

    /**
     * Add the given client.
     *
     * @param client
     *
     * @throws ValidatorException
     *             if the client is not valid.
     */
    public void addClient(Client client) throws ValidatorException {
        Optional<Client> findClient = repository.findOne(client.getId());
        if (findClient.isPresent()) {
            throw new ValidatorException("Id already taken");
        }
        try {
            clientValidator.validate(client);
            repository.save(client);
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all the clients.
     *
     * @return all clients
     */
    public Iterable<Client> getAllClients() {
        //OLD LAB IMPLEMENTATION
        return repository.findAll();

        //NEW SORT IMPLEMENTATION
        //Sort sort = new Sort("age").and(new Sort(Boolean.FALSE, "id"));
        //return repository.findAll(sort);
    }

    /**
     * Delete the given client.
     *
     * @param id
     *
     * @throws ValidatorException
     *             if the client id is not present.
     */
    public void deleteClient(Long id) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {
        Optional<Client> findClient = repository.findOne(id);
        if (!findClient.isPresent()) {
            throw new ValidatorException("Cannot find ID");
        }
        repository.delete(id);
    }

    public Set<Long> getIds() {
        Iterable<Client> clients = repository.findAll();
        return StreamSupport.stream(clients.spliterator(), false)
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }

    /**
     * Update the given client.
     *
     * @param client
     *
     * @throws ValidatorException
     *             if the client is not valid.
     */
    public void updateClient(Client client) throws ValidatorException {
        Optional<Client> findClient = repository.findOne(client.getId());
        if (!findClient.isPresent()) {
            throw new ValidatorException("Cannot find ID");
        }
        try {
            clientValidator.validate(client);
            repository.update(client);
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        } catch (IOException | SQLException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
