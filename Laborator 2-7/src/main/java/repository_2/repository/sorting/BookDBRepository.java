package repository_2.repository.sorting;

import org.xml.sax.SAXException;
import repository_2.domain.Book;
import repository_2.domain.Client;
import repository_2.domain.validators.BookStoreException;
import repository_2.domain.validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BookDBRepository implements SortingRepository<Long, Book> {
    private final String url;
    private final String user;
    private final String password;

    public BookDBRepository(String url,String user,String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Optional<Book> parseResultSet(ResultSet rs) {
        try {
            Long id = rs.getLong("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            int price = rs.getInt("price");

            Book book = new  Book(title,author,price);
            book.setId(id);
            return Optional.of(book);

        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Book> findAll(Sort sort) {
        //Get all books from the method findAll()
        List<Book> books = (List<Book>) this.findAll();

        //Create an instance of SortAlgorithm with:
        //  - the list of books
        //  - the sorting condition
        SortAlgorithm<Long, Book> sortList = new SortAlgorithm<>(books, sort);

        //Call the method sort() and return the new list sorted
        return sortList.sort();
    }

    @Override
    public Optional<Book> findOne(Long id) {
        Optional.ofNullable(id).orElseThrow(()->new IllegalArgumentException("ID must not be null"));

        String sqlStatement = "SELECT * FROM book WHERE id = " + id;

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
    public Iterable<Book> findAll() {
        List<Book> books = new ArrayList<>();

        String sqlStatement = "SELECT * FROM book";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Optional<Book> res = parseResultSet(rs);
                res.ifPresent(books::add);
            }

            return books;

        } catch (SQLException e) {
            throw new BookStoreException(e.getMessage().toString());
        }
    }

    @Override
    public Optional<Book> save(Book book) throws ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        Optional.ofNullable(book).orElseThrow(()->new IllegalArgumentException("Book must not be null"));

        Optional<Book> foundBook = findOne(book.getId());

        // check for duplicate books
        if (foundBook.isPresent())
            return foundBook;

        String sqlStatement = "INSERT INTO book (id, title, author, price) VALUES (?,?,?,?)";

        System.out.println("Reach here 1");
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement)
        ) {
            System.out.println("reach here 2");
            statement.setLong(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getPrice());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<Book> delete(Long id) throws IllegalArgumentException, ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        Optional.ofNullable(id).orElseThrow(()->new IllegalArgumentException("ID must not be null"));

        String sqlStatement = "DELETE from book where id = ?";
        Optional<Book> foundBook = findOne(id);

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sqlStatement)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return foundBook;
    }

    @Override
    public Optional<Book> update(Book book) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException, SQLException {
        Optional.ofNullable(book).orElseThrow(() -> new IllegalArgumentException("Book must not be null"));

        String sql = "UPDATE book set title = ?, author = ?, price = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPrice());
            statement.setLong(4, book.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        return Optional.of(book);
    }
}
