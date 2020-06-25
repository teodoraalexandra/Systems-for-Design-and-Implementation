package repository_2.repository.sorting;

import javafx.util.Pair;
import org.xml.sax.SAXException;
import repository_2.domain.Book;
import repository_2.domain.Client;
import repository_2.domain.Transaction;
import repository_2.domain.validators.BookStoreException;
import repository_2.domain.validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDBRepository implements SortingRepository<Pair<Long, Long>, Transaction>{

    private final String url;
    private final String user;
    private final String password;

    public TransactionDBRepository(String url,String user,String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Optional<Transaction> parseResultSet(ResultSet rs) {
        try {
            Long idBook = rs.getLong(("bookid"));
            Long idClient = rs.getLong("clientid");
            int transactionNumber = rs.getInt("transactionnumber");
            String transactionCode = rs.getString("transactioncode");
            String orderDate = rs.getString("orderdate");

            Transaction transaction = new Transaction(transactionNumber,transactionCode,orderDate);
            Pair<Long, Long> idTransaction = new Pair<>(idBook, idClient);
            transaction.setId(idTransaction);
            return Optional.of(transaction);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Transaction> findAll(Sort sort) {
        //Get all transactions from the method findAll()
        List<Transaction> transactions = (List<Transaction>) this.findAll();

        //Create an instance of SortAlgorithm with:
        //  - the list of transactions
        //  - the sorting condition
        SortAlgorithm<Pair<Long, Long>, Transaction> sortList = new SortAlgorithm<>(transactions, sort);

        //Call the method sort() and return the new list sorted
        return sortList.sort();
    }

    @Override
    public Optional<Transaction> findOne(Pair<Long, Long> id) {
        Optional.ofNullable(id).orElseThrow(()->new IllegalArgumentException("ID must not be null"));

        String sqlStatement = "SELECT * FROM transaction WHERE bookid = ? and clientid = ? ";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement);
             ) {
            statement.setLong(1, id.getKey());
            statement.setLong(2, id.getValue());
            ResultSet rs = statement.executeQuery();

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
    public Iterable<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();

        String sqlStatement = "SELECT * FROM transaction";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Optional<Transaction> res = parseResultSet(rs);
                res.ifPresent(transactions::add);
            }

            return transactions;

        } catch (SQLException e) {
            throw new BookStoreException(e.getMessage().toString());
        }
    }

    @Override
    public Optional<Transaction> save(Transaction transaction) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        Optional.ofNullable(transaction).orElseThrow(()->new IllegalArgumentException("Transaction must not be null"));

        Optional<Transaction> foundTransaction = findOne(transaction.getId());

        if (foundTransaction.isPresent())
            return foundTransaction;

        String sqlStatement = "INSERT INTO transaction (bookid, clientid, transactionnumber, transactioncode, orderdate) VALUES (?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement)
        ) {
            statement.setLong(1, transaction.getId().getKey());
            statement.setLong(2, transaction.getId().getValue());
            statement.setInt(3, transaction.getTransactionNumber());
            statement.setString(4, transaction.getTransactionCode());
            statement.setString(5, transaction.getOrderDate());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<Transaction> delete(Pair<Long, Long> id) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        Optional.ofNullable(id).orElseThrow(()->new IllegalArgumentException("ID must not be null"));

        String sqlStatement = "DELETE from transaction where bookid = ? and clientid = ?";
        Optional<Transaction> foundTransaction = findOne(id);

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement)
        ) {
            statement.setLong(1, id.getKey());
            statement.setLong(2, id.getValue());
            statement.executeUpdate();
        } catch (SQLException e) {
            return Optional.empty();
        }

        return (foundTransaction);
    }

    @Override
    public Optional<Transaction> update(Transaction transaction) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        Optional.ofNullable(transaction).orElseThrow(()->new IllegalArgumentException("Transaction must not be null"));

        String sql = "UPDATE transaction set transactionnumber = ?, transactioncode = ?, orderdate = ? where bookid = ? and clientid = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, transaction.getTransactionNumber());
            statement.setString(2, transaction.getTransactionCode());
            statement.setString(3, transaction.getOrderDate());
            statement.setLong(4, transaction.getId().getKey());
            statement.setLong(5, transaction.getId().getValue());
            statement.executeUpdate();

        } catch (SQLException e) {
            return Optional.empty();
        }

        return Optional.of(transaction);
    }
}
