package repository_2.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientTest {
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final String FIRST_NAME = "firstName1";
    private static final String NEW_FIRST_NAME = "firstName2";
    private static final String LAST_NAME = "lastName1";
    private static final String NEW_LAST_NAME = "lastName2";
    private static final int AGE = 25;
    private static final int NEW_AGE = 30;

    private Client client;

    @Before
    public void setUp() throws Exception {
        client = new Client(FIRST_NAME, LAST_NAME, AGE);
        client.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        client = null;
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, client.getId());
    }

    @Test
    public void testSetId() throws Exception {
        client.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, client.getId());
    }

    @Test
    public void testGetFirstName() throws Exception {
        assertEquals("First Name should be equal", FIRST_NAME, client.getFirstName());
    }

    @Test
    public void testSetFirstName() throws Exception {
        client.setFirstName(NEW_FIRST_NAME);
        assertEquals("First Name should be equal", NEW_FIRST_NAME, client.getFirstName());
    }

    @Test
    public void testGetLastName() throws Exception {
        assertEquals("Last Name should be equal", LAST_NAME, client.getLastName());
    }

    @Test
    public void testSetLastName() throws Exception {
        client.setLastName(NEW_LAST_NAME);
        assertEquals("Last Name should be equal", NEW_LAST_NAME, client.getLastName());
    }

    @Test
    public void testGetAge() throws Exception {
        assertEquals("Age should be equal", AGE, client.getAge());
    }

    @Test
    public void testSetAge() throws Exception {
        client.setAge(NEW_AGE);
        assertEquals("Age should be equal", NEW_AGE, client.getAge());
    }
}
