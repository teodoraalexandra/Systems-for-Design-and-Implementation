package socket.server.service;

import org.xml.sax.SAXException;
import socket.common.ClientService;
import socket.common.domain.Book;
import socket.common.domain.Client;
import socket.common.domain.validators.BookValidator;
import socket.common.domain.validators.ClientValidator;
import socket.common.domain.validators.Validator;
import socket.common.utilities.Conversion;
import socket.server.repository.BookDBRepository;
import socket.server.repository.ClientDBRepository;
import socket.server.repository.SortingRepository;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {
    private static final String URL = "jdbc:postgresql://localhost:5432/book-store";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private ExecutorService executorService;
    private ClientServiceServer clientServiceServer;

    public ClientServiceImpl(ExecutorService executorService) {
        Validator<Client> clientValidator = new ClientValidator();
        SortingRepository<Long, Client> clientRepository = new ClientDBRepository(URL, USER, PASSWORD);
        this.clientServiceServer = new ClientServiceServer(clientRepository, clientValidator);
        this.executorService = executorService;
    }

    @Override
    public String addClient(String fileName) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // Deserialization
        Conversion conversion = new Conversion(fileName);
        List<Client> clientList = conversion.xmlToClient();
        Client client = clientList.get(0);
        //System.out.println("Client read from xml is: " + client);
        this.clientServiceServer.addClient(client);
        //if this is correct -> delete the client from xml file

        conversion.deleteClientFromFile(client.getId());

        return "Client added successfully";
    }


    @Override
    public String updateClient(String fileName) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        // Deserialization
        Conversion conversion = new Conversion(fileName);
        List<Client> clientList = conversion.xmlToClient();
        Client client = clientList.get(0);
        //System.out.println("Client read from xml is: " + client);
        this.clientServiceServer.updateClient(client);
        //if this is correct -> delete the client from xml file

        conversion.deleteClientFromFile(client.getId());

        return "Client updated successfully";
    }

    @Override
    public String deleteClient(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        Long idConverted = Long.valueOf(id).longValue();
        this.clientServiceServer.deleteClient(idConverted);

        return "Client deleted successfully";
    }

    @Override
    public String printClients() throws IOException {
        Iterable<Client> clients = this.clientServiceServer.getAllClients();

        ArrayList<Client> result = new ArrayList<Client>();
        clients.forEach(result::add);

        String csvFileName = "/Users/teodoradan/Desktop/repository_2/common/src/main/java/socket/common/utilities/clients.csv";
        Conversion conversion = new Conversion(csvFileName);
        conversion.writeClientToCSV(result);

        return csvFileName;
    }
}
