package repository_2.repository.file;


import javafx.util.Pair;
import org.xml.sax.SAXException;
import repository_2.domain.Transaction;
import repository_2.repository.inmemory.InMemoryRepository;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class TransactionFileRepository extends InMemoryRepository<Pair<Long, Long>, Transaction> {
    private String fileName;

    public TransactionFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long idBook = Long.valueOf(items.get(0));
                Long idClient = Long.valueOf(items.get(1));
                int transactionNumber = Integer.parseInt(items.get(2));
                String transactionCode = items.get(3);
                String orderDate = items.get(4);

                Pair<Long, Long> idTransaction = new Pair(idBook, idClient);

                Transaction transaction = new Transaction(transactionNumber, transactionCode, orderDate);
                transaction.setId(idTransaction);
                try {
                    super.save(transaction);
                } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves the given book to a file and in memory.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    @Override
    public Optional<Transaction> save(Transaction entity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Optional<Transaction> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Transaction entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId().getKey() + "," + entity.getId().getValue() + "," + entity.getTransactionNumber() + "," + entity.getTransactionCode() + "," + entity.getOrderDate());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Delete the given book from a file and from memory.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    @Override
    public Optional<Transaction> delete(Pair<Long, Long> id) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        deleteFromFile(id);
        return super.delete(id);
    }

    private void deleteFromFile(Pair <Long, Long> idToDelete) {
        File file = new File("./data/transactions");
        try {
            List<String> out = Files.lines(file.toPath())
                    .filter(line -> !Long.valueOf(Arrays.asList(line.split(",")).get(0)).equals(idToDelete.getKey()) || !Long.valueOf(Arrays.asList(line.split(",")).get(1)).equals(idToDelete.getValue()))
                    .collect(Collectors.toList());
            Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the given book.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    @Override
    public Optional<Transaction> update(Transaction entity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        updateFromFile(entity);
        return super.update(entity);
    }

    private void updateFromFile(Transaction entity) {
        File file = new File("./data/transactions");

        try {
            List<String> out = Files.lines(file.toPath())
                    .map(line -> {
                        if (Long.valueOf(Arrays.asList(line.split(",")).get(0)).equals(entity.getId().getKey()) && Long.valueOf(Arrays.asList(line.split(",")).get(1)).equals(entity.getId().getValue())) {
                            return entity.getId().getKey() + "," + entity.getId().getValue() + "," + entity.getTransactionNumber() + "," + entity.getTransactionCode() + "," + entity.getOrderDate();
                        } else {
                            return line;
                        }
                    })
                    .collect(Collectors.toList());
            Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}