package service;

import org.springframework.beans.factory.annotation.Autowired;

import domain.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientServiceClient implements ClientService {

    @Autowired
    ClientService ClientService;

    @Override
    public Client findClient(Long id)  {
        System.out.println("Client ---> call method findClient(" + id + ")\n");
        return ClientService.findClient(id);
    }

    @Override
    public void addClient(Client Client)  {
        System.out.println("Client ---> call method addClient(" + Client + ")\n");
        ClientService.addClient(Client);
    }

    @Override
    public void updateClient(Client Client)  {
        System.out.println("Client ---> call method updateClient(" + Client + ")\n");
        ClientService.updateClient(Client);
    }

    @Override
    public void deleteClient(Long id)  {
        System.out.println("Client ---> call method deleteClient(" + id + ")\n");
        ClientService.deleteClient(id);
    }

    @Override
    public List<Client> findAllClients()  {
        System.out.println("Client ---> call method findAllClients()\n");
        return ClientService.findAllClients();
    }

}