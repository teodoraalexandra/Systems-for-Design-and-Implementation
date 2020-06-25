package repository_2.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import repository_2.domain.Client;
import repository_2.repository.inmemory.InMemoryRepository;

import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class InMemoryRepositoryTest {
    private InMemoryRepository<Long, Client> TestRepo;

    private static final Long ID = new Long(1);
    private static final String FIRST_NAME = "firstName1";
    private static final String LAST_NAME = "lastName1";
    private static final int AGE = 25;

    private Client client;

    @Before
    public void setUp() throws Exception {
        TestRepo = new InMemoryRepository();
    }

    @After
    public void tearDown() throws Exception {
        TestRepo = null;
    }

    @Test
    public void testSave() throws Exception {
        client = new Client(FIRST_NAME, LAST_NAME, AGE);
        client.setId(ID);
        TestRepo.save(client);
        assertEquals("Length of entities should be 1", 1, StreamSupport.stream(TestRepo.findAll().spliterator(), false).count());
        client = null;
    }

    @Test
    public void testFindOne() throws Exception {
        Long id = Long.valueOf((2));
        Client client = new Client("firstName2", "lastNAme2", 32);
        client.setId(id);
        TestRepo.save(client);
        assertEquals(TestRepo.findOne(id).isPresent(),true);
        assertEquals(client,TestRepo.findOne(id).get());
    }

    @Test
    public void testDelete() throws Exception {
        Long id = Long.valueOf((3));
        Client client = new Client("firstName3", "lastNAme3", 32);
        client.setId(id);
        TestRepo.save(client);
        TestRepo.delete(id);
        assertEquals("there should not be any client with id 3 in repository",0,StreamSupport.stream(TestRepo.findAll().spliterator(),false).count());
    }

    @Test
    public void testUpdate() throws Exception {
        Long id = Long.valueOf((4));
        Client client = new Client("firstName4", "lastNAme4", 32);
        client.setId(id);
        TestRepo.save(client);
        Client client1 = new Client("firstName5", "lastNAme5", 32);
        client1.setId(id);
        TestRepo.update(client1);
        assertEquals(TestRepo.findOne(id).isPresent(),true);
        assertEquals(client1,TestRepo.findOne(id).get());
    }

}
