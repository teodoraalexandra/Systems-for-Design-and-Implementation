package socket.server.service;

import javafx.util.Pair;
import org.xml.sax.SAXException;
import socket.common.TransactionService;
import socket.common.domain.Book;
import socket.common.domain.Transaction;
import socket.common.domain.validators.BookValidator;
import socket.common.domain.validators.TransactionValidator;
import socket.common.domain.validators.Validator;
import socket.common.utilities.Conversion;
import socket.server.repository.BookDBRepository;
import socket.server.repository.SortingRepository;
import socket.server.repository.TransactionDBRepository;

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

public class TransactionServiceImpl implements TransactionService {
    private static final String URL = "jdbc:postgresql://localhost:5432/book-store";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private ExecutorService executorService;
    private TransactionServiceServer transactionServiceServer;
    private BookServiceServer bookService;
    private ClientServiceServer clientService;

    public TransactionServiceImpl(ExecutorService executorService) {
        Validator<Transaction> transactionValidator = new TransactionValidator();
        SortingRepository<Pair<Long, Long>, Transaction> transactionRepository = new TransactionDBRepository(URL, USER, PASSWORD);
        this.transactionServiceServer = new TransactionServiceServer(transactionRepository, transactionValidator);
        this.executorService = executorService;
    }

    @Override
    public String addTransaction(String fileName) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // Deserialization
        Conversion conversion = new Conversion(fileName);
        List<Transaction> transactionList = conversion.xmlToTransaction();
        Transaction transaction = transactionList.get(0);
        //System.out.println("Transaction read from xml is: " + transaction);

        this.transactionServiceServer.addTransaction(transaction);
        //if this is correct -> delete the book from xml file

        conversion.deleteTransactionFromFile(transaction.getId());

        return "Transaction added successfully";
    }


    @Override
    public String updateTransaction(String fileName) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        // Deserialization
        Conversion conversion = new Conversion(fileName);
        List<Transaction> transactionList = conversion.xmlToTransaction();
        Transaction transaction = transactionList.get(0);
        //System.out.println("Transaction read from xml is: " + transaction);

        this.transactionServiceServer.updateTransaction(transaction);
        //if this is correct -> delete the book from xml file

        conversion.deleteTransactionFromFile(transaction.getId());

        return "Transaction updated successfully";
    }

    @Override
    public String deleteTransaction(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        String[] parts = id.split("-");
        String book_id = parts[0];
        String client_id = parts[1];
        Long idBookConverted = Long.valueOf(book_id);
        Long idClientConverted = Long.valueOf(client_id);
        Pair<Long, Long> transaction_id = new Pair<>(idBookConverted, idClientConverted);

        this.transactionServiceServer.deleteTransaction(transaction_id);

        return "Transaction deleted successfully";
    }

    @Override
    public String deleteTransactionByBookId(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        Long idConverted = Long.valueOf(id);
        this.transactionServiceServer.deleteTransactionByBookId(idConverted);

        return "Transaction with given book id deleted successfully";
    }

    @Override
    public String deleteTransactionByClientId(String id) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        Long idConverted = Long.valueOf(id);
        this.transactionServiceServer.deleteTransactionByClientId(idConverted);

        return "Transaction with given client id deleted successfully";
    }

    @Override
    public String printTransactions() throws IOException {
        Iterable<Transaction> transactions = this.transactionServiceServer.getAllTransactions();

        ArrayList<Transaction> result = new ArrayList<Transaction>();
        transactions.forEach(result::add);

        String csvFileName = "/Users/teodoradan/Desktop/repository_2/common/src/main/java/socket/common/utilities/transactions.csv";
        Conversion conversion = new Conversion(csvFileName);
        conversion.writeTransactionToCSV(result);

        return csvFileName;
    }
}
