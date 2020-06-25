package repository_2.repository.file;


import org.xml.sax.SAXException;
import repository_2.domain.Client;
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

public class ClientFileRepository extends InMemoryRepository<Long, Client> {
    private String fileName;

    public ClientFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String firstName = items.get(1);
                String lastName = items.get(2);
                int age = Integer.parseInt(items.get(3));

                Client client = new Client(firstName, lastName, age);
                client.setId(id);
                try {
                    super.save(client);
                } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves the given client to a file and in memory.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    @Override
    public Optional<Client> save(Client entity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Client entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                     entity.getId() + "," + entity.getFirstName() + "," + entity.getLastName() + "," + entity.getAge());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete the given client from a file and from memory.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    @Override
    public Optional<Client> delete(Long id) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        deleteFromFile(id);
        return super.delete(id);
    }

    private void deleteFromFile(Long idToDelete) {
        File file = new File("./data/clients");
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
     * Updates the given client.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    @Override
    public Optional<Client> update(Client entity) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        updateFromFile(entity);
        return super.update(entity);
    }

    private void updateFromFile(Client entity) {
        File file = new File("./data/clients");

        try {
            List<String> out = Files.lines(file.toPath())
                    .map(line -> {
                        if (Long.valueOf(Arrays.asList(line.split(",")).get(0)).equals(entity.getId())) {
                             return entity.getId() + "," + entity.getFirstName() + "," + entity.getLastName() + "," + entity.getAge();
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