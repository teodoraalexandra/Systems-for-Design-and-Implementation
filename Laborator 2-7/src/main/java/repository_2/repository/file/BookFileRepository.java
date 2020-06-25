package repository_2.repository.file;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.xml.sax.SAXException;
import repository_2.domain.Book;
import repository_2.repository.inmemory.InMemoryRepository;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Optional;

public class BookFileRepository extends InMemoryRepository<Long, Book> {
    private String fileName;

    public BookFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String title = items.get(1);
                String author = items.get(2);
                int price = Integer.parseInt(items.get(3));

                Book book = new Book(title, author, price);
                book.setId(id);
                try {
                    super.save(book);
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
    public Optional<Book> save(Book entity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Optional<Book> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Book entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getTitle() + "," + entity.getAuthor() + "," + entity.getPrice());
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
    public Optional<Book> delete(Long id) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        deleteFromFile(id);
        return super.delete(id);
    }

    private void deleteFromFile(Long idToDelete) {
        File file = new File("./data/books");
        try {
            List<String> out = Files.lines(file.toPath())
                    .filter(line -> !Long.valueOf(Arrays.asList(line.split(",")).get(0)).equals(idToDelete))
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
    public Optional<Book> update(Book entity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        updateFromFile(entity);
        return super.update(entity);
    }

    private void updateFromFile(Book entity) {
        File file = new File("./data/books");

        try {
            List<String> out = Files.lines(file.toPath())
                    .map(line -> {
                        if (Long.valueOf(Arrays.asList(line.split(",")).get(0)).equals(entity.getId())) {
                            return entity.getId() + "," + entity.getTitle() + "," + entity.getAuthor() + "," + entity.getPrice();
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