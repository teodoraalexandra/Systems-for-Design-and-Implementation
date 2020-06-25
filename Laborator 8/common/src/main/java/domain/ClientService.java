package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface ClientService {

    List<Client> findAllClients();
    Client findClient(Long id);
    void addClient(Client Client);
    void updateClient(Client Client);
    void deleteClient(Long id);
}