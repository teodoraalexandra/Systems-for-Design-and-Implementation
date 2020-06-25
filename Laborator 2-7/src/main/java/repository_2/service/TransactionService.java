package repository_2.service;

import javafx.util.Pair;
import org.xml.sax.SAXException;
import repository_2.domain.BaseEntity;
import repository_2.domain.Client;
import repository_2.domain.Transaction;
import repository_2.domain.validators.Validator;
import repository_2.domain.validators.ValidatorException;
import repository_2.repository.Repository;
import repository_2.repository.sorting.Sort;
import repository_2.repository.sorting.SortingRepository;
import static com.google.common.collect.Sets.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TransactionService {
    //private Repository<Pair<Long, Long>, Transaction> repository;
    private SortingRepository<Pair<Long, Long>, Transaction> repository;
    private Validator<Transaction> transactionValidator;
    private BookService bookService;
    private ClientService clientService;

    public TransactionService(SortingRepository<Pair<Long, Long>, Transaction> repository, Validator<Transaction> transactionValidator, BookService bookService, ClientService clientService) {
        this.repository = repository;
        this.transactionValidator = transactionValidator;
        this.bookService = bookService;
        this.clientService = clientService;
    }

    /**
     * Add the given transaction.
     *
     * @param transaction
     *
     * @throws ValidatorException
     *             if the transaction is not valid.
     */
    public void addTransaction(Transaction transaction) throws ValidatorException {
        Set<Long> bookIds = bookService.getIds();
        Set<Long> clientIds = clientService.getIds();

        Optional<Transaction> findTransaction = repository.findOne(transaction.getId());
        if (findTransaction.isPresent()) {
            throw new ValidatorException("Id already taken");
        }
        try {
            transactionValidator.validate(transaction);
            if (bookIds.contains(transaction.getId().getKey()) && clientIds.contains(transaction.getId().getValue())) {
                repository.save(transaction);
            } else {
                throw new ValidatorException("One of transaction Ids are not part of client or book Ids.");
            }
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get all the transactions.
     *
     * @return all transactions
     */
    public Iterable<Transaction> getAllTransactions() {
        //OLD LAB IMPLEMENTATION
        //return repository.findAll();

        //NEW SORT IMPLEMENTATION
        Sort sort = new Sort("transactionCode");
        return repository.findAll(sort);
    }

    /**
     * Delete the given transaction.
     *
     * @param id
     *
     * @throws ValidatorException
     *             if the transaction id is not present.
     */
    public void deleteTransaction(Pair<Long, Long> id) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {
        Optional<Transaction> findTransaction = repository.findOne(id);
        if (!findTransaction.isPresent()) {
            throw new ValidatorException("Cannot find ID");
        }
        repository.delete(id);
    }

    /**
     * Delete the transaction after deleting a Book.
     *
     * @param id
     *
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws SAXException
     * @throws IOException
     */
    public void deleteTransactionByBookId(Long id) throws ParserConfigurationException, TransformerException, SAXException, IOException{
        Set<Transaction> transactions = StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(transaction -> transaction.getId().getKey().equals(id))
                .collect(Collectors.toSet());
        transactions.stream().forEach(transaction -> {
            try {
                repository.delete(transaction.getId());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Delete the transaction after deleting a Client.
     *
     * @param id
     *
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws SAXException
     * @throws IOException
     */
    public void deleteTransactionByClientId(Long id) throws ParserConfigurationException, TransformerException, SAXException, IOException{
        Set<Transaction> transactions = StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(transaction -> transaction.getId().getValue().equals(id))
                .collect(Collectors.toSet());
        transactions.stream().forEach(transaction -> {
            try {
                repository.delete(transaction.getId());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }
    /**
     * Update the given transaction.
     *
     * @param transaction
     *
     * @throws ValidatorException
     *             if the transaction is not valid.
     */
    public void updateTransaction(Transaction transaction) throws ValidatorException {
        Optional<Transaction> findTransaction = repository.findOne(transaction.getId());
        if (!findTransaction.isPresent()) {
            throw new ValidatorException("Cannot find ID");
        }
        try {
            transactionValidator.validate(transaction);
            repository.update(transaction);
        } catch (ValidatorException e) {
            throw  new ValidatorException(e.getMessage());
        } catch (IOException | SQLException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

    }
}