package repository;

import java.util.List;
import java.util.Optional;

import domain.Client;

public interface ClientRepo {
    List<Client> findAllClients();
    void save(Client Client);
    void update(Client Client);
    void delete(Long id);
    Optional<Client> findOne(Long id);
}