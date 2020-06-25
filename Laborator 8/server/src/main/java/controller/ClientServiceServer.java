package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.*;
import repository.ClientRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceServer implements ClientService {

    @Autowired
    private ClientRepo clientRepo;

    @Override
    public List<Client> findAllClients() {
        List<Client> result = clientRepo.findAllClients();
        System.out.println("ClientServiceServer: findAllClients() = " + result);

        return result;
    }

    @Override
    public Client findClient(Long id) {
        Client client = clientRepo.findOne(id).get();
        System.out.println("ClientServiceServer: findClient = " + client);

        return client;

    }

    @Override
    public void addClient(Client client) {
        clientRepo.save(client);

        System.out.println("Add successfully! -- ClientServiceServer");
    }

    @Override
    @Transactional
    public void updateClient(Client client) {
        Optional<Client> clientOptional = clientRepo.findOne(client.getId());

        if (clientOptional.isPresent()) {
            clientRepo.update(client);
        }

        System.out.println("Update successfully: ClientServiceServer");
    }

    @Override
    public void deleteClient(Long id) {
        clientRepo.delete(id);
        System.out.println("Delete successfully: ClientServiceServer");
    }
}