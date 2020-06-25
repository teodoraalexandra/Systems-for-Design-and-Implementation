package lab10.core.service;

import lab10.core.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    Client saveClient(Client client);

    Client updateClient(Long id, Client client);

    void deleteClientById(Long id);
}
