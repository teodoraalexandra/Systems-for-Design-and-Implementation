package socket.common;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;

public interface BookService {
    String addBook = "adding book";
    String updateBook = "updating book";
    String deleteBook = "deleting book";
    String printBooks = "printing books";

    String addBook(String fileName) throws ParserConfigurationException, IOException, SAXException, TransformerException;
    String updateBook(String fileName) throws ParserConfigurationException, TransformerException, SAXException, IOException;
    String deleteBook(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException;
    String printBooks() throws IOException;
}
