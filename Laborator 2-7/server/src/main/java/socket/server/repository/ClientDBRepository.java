package socket.server.repository;

import org.xml.sax.SAXException;
import socket.common.domain.Book;
import socket.common.domain.Client;
import socket.common.domain.validators.BookStoreException;
import socket.common.domain.validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDBRepository implements SortingRepository<Long, Client>{
    private final String url;
    private final String user;
    private final String password;

    public ClientDBRepository(String url,String user,String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Optional<Client> parseResultSet(ResultSet rs) {
        try {
            Long id = rs.getLong("id");
            String firstName = rs.getString("firstname");
            String LastName = rs.getString("lastname");
            int age = rs.getInt("age");

            Client client = new Client(firstName,LastName,age);
            client.setId(id);
            return Optional.of(client);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Client> findAll(Sort sort) {
        //Get all clients from the method findAll()
        List<Client> clients = (List<Client>) this.findAll();

        //Create an instance of SortAlgorithm with:
        //  - the list of clients
        //  - the sorting condition
        SortAlgorithm<Long, Client> sortList = new SortAlgorithm<>(clients, sort);

        //Call the method sort() and return the new list sorted
        return sortList.sort();
    }

    @Override
    public Optional<Client> findOne(Long id) {
        Optional.ofNullable(id).orElseThrow(()->new IllegalArgumentException("ID must not be null"));

        String sqlStatement = "SELECT * FROM client WHERE id = " + id;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return parseResultSet(rs);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new BookStoreException("An error has occurred while reading from the database!", e);
        }
    }

    @Override
    public Iterable<Client> findAll() {
        List<Client> clients = new ArrayList<>();

        String sqlStatement = "SELECT * FROM client";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Optional<Client> res = parseResultSet(rs);
                res.ifPresent(clients::add);
            }

            System.out.println();
            return clients;

        } catch (SQLException e) {
            throw new BookStoreException(e.getMessage().toString());
        }
    }

    @Override
    public Optional<Client> save(Client client) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {

        Optional.ofNullable(client).orElseThrow(()->new IllegalArgumentException("Client must not be null"));

        Optional<Client> foundClient = findOne(client.getId());

        // check for duplicate clients
        if (foundClient.isPresent())
            return foundClient;

        String sqlStatement = "INSERT INTO client (id, firstname, lastname, age) VALUES (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement)
        ) {

            statement.setLong(1, client.getId());
            statement.setString(2, client.getFirstName());
            statement.setString(3, client.getLastName());
            statement.setInt(4, client.getAge());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Long id) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        Optional.ofNullable(id).orElseThrow(()->new IllegalArgumentException("ID must not be null"));

        String sqlStatement = "DELETE from client where id = ?";
        Optional<Client> foundClient = findOne(id);

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return (foundClient);
    }

    @Override
    public Optional<Client> update(Client client) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        Optional.ofNullable(client).orElseThrow(() -> new IllegalArgumentException("Client must not be null"));

        String sql = "UPDATE client set firstname = ?, lastname = ?, age = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setInt(3, client.getAge());
            statement.setLong(4, client.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return Optional.of(client);
    }

}

