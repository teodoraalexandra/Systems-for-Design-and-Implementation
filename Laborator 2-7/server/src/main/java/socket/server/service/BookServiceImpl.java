package socket.server.service;

import org.xml.sax.SAXException;
import socket.common.BookService;
import socket.common.domain.validators.BookValidator;
import socket.common.domain.validators.Validator;
import socket.common.utilities.Conversion;
import socket.server.repository.BookDBRepository;
import socket.server.repository.SortingRepository;
import socket.common.domain.Book;
import socket.common.utilities.Conversion;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private static final String URL = "jdbc:postgresql://localhost:5432/book-store";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private ExecutorService executorService;
    private BookServiceServer bookServiceServer;

    public BookServiceImpl(ExecutorService executorService) {
        Validator<Book> bookValidator = new BookValidator();
        SortingRepository<Long, Book> bookRepository = new BookDBRepository(URL, USER, PASSWORD);
        this.bookServiceServer = new BookServiceServer(bookRepository, bookValidator);
        this.executorService = executorService;
    }

    @Override
    public String addBook(String fileName) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // Deserialization
        Conversion conversion = new Conversion(fileName);
        List<Book> bookList = conversion.xmlToBook();
        Book book = bookList.get(0);
        //System.out.println("Book read from xml is: " + book);
        this.bookServiceServer.addBook(book);
        //if this is correct -> delete the book from xml file

        conversion.deleteBookFromFile(book.getId());

        return "Book added successfully";
    }


    @Override
    public String updateBook(String fileName) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        // Deserialization
        Conversion conversion = new Conversion(fileName);
        List<Book> bookList = conversion.xmlToBook();
        Book book = bookList.get(0);
        //System.out.println("Book read from xml is: " + book);
        this.bookServiceServer.updateBook(book);
        //if this is correct -> delete the book from xml file

        conversion.deleteBookFromFile(book.getId());

        return "Book updated successfully";
    }

    @Override
    public String deleteBook(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        Long idConverted = Long.valueOf(id).longValue();
        this.bookServiceServer.deleteBook(idConverted);

        return "Book deleted successfully";
    }

    @Override
    public String printBooks() throws IOException {
        Iterable<Book> books = this.bookServiceServer.getAllBooks();
        ArrayList<Book> result = new ArrayList<Book>();
        books.forEach(result::add);

        String csvFileName = "/Users/teodoradan/Desktop/repository_2/common/src/main/java/socket/common/utilities/books.csv";
        Conversion conversion = new Conversion(csvFileName);
        conversion.writeBookToCSV(result);

        return csvFileName;
    }
}
