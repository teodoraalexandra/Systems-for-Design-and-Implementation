package lab9.service;

import lab9.model.Client;
import java.util.List;


public interface ClientService {
    List<Client> getAllClients();

    void saveClient(Client client);

    void updateClient(Client client);

    void deleteClientById(Long id);
}
