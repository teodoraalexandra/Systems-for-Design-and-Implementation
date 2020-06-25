package socket.server.service;

import org.xml.sax.SAXException;
import socket.common.domain.BaseEntity;
import socket.common.domain.Book;
import socket.common.domain.validators.Validator;
import socket.common.domain.validators.ValidatorException;
import socket.server.repository.Repository;
import socket.server.repository.Sort;
import socket.server.repository.SortingRepository;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.common.collect.Sets.newHashSet;

public class BookServiceServer {
    //private Repository<Long, Book> repository;
    private SortingRepository<Long, Book> repository;
    private Validator<Book> bookValidator;

    public BookServiceServer(SortingRepository<Long, Book> repository, Validator<Book> bookValidator) {
        this.repository = repository;
        this.bookValidator = bookValidator;
    }

    /**
     * Add the given book.
     *
     * @param book
     *
     * @throws ValidatorException
     *             if the book is not valid.
     */
    public void addBook(Book book) throws ValidatorException {
        Optional<Book> findBook = repository.findOne(book.getId());
        if (findBook.isPresent()) {
            throw new ValidatorException("Id is already taken");
        }

        try{
            bookValidator.validate(book);
            repository.save(book);
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all the books.
     *
     * @return all books
     */
    public Iterable<Book> getAllBooks() {
        //OLD LAB IMPLEMENTATION
        return repository.findAll();

        //NEW SORT IMPLEMENTATION
        //Sort sort = new Sort(Boolean.FALSE, "price");
        //return repository.findAll(sort);
    }

    Set<Long> getIds() {
        Iterable<Book> books = repository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }

    /**
     * Delete the given book
     *
     * @param id
     *
     * @throws ValidatorException
     *             if the book id is not present.
     */
    public void deleteBook(Long id) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {
        Optional<Book> findBook = repository.findOne(id);
        if (!findBook.isPresent()) {
            throw new ValidatorException("Cannot find ID");
        }
        repository.delete(id);
    }

    /**
     * Update the given book.
     *
     * @param book
     *
     * @throws ValidatorException
     *             if the book is not valid.
     */
    public void updateBook(Book book) throws ValidatorException {
        Optional<Book> findBook = repository.findOne(book.getId());
        if (!findBook.isPresent()) {
            throw new ValidatorException("Cannot find ID");
        }
        try {
            bookValidator.validate(book);
            repository.update(book);
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        } catch (IOException | SQLException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

}
