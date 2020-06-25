package lab9.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientTest {
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final String SERIAL_NUMBER = "serial_number_1";
    private static final String NEW_SERIAL_NUMBER = "serial_number_2";
    private static final String NAME = "name1";
    private static final String NEW_NAME = "name2";

    private Client client;

    @Before
    public void setUp() throws Exception {
        client = new Client(SERIAL_NUMBER, NAME);
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
    public void testGetSerialNumber() throws Exception {
        assertEquals("Serial number should be equal", SERIAL_NUMBER, client.getSerialNumber());
    }

    @Test
    public void testSetSerialNumber() throws Exception {
        client.setSerialNumber(NEW_SERIAL_NUMBER);
        assertEquals("Serial number should be equal", NEW_SERIAL_NUMBER, client.getSerialNumber());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Name should be equal", NAME, client.getName());
    }

    @Test
    public void testSetName() throws Exception {
        client.setName(NEW_NAME);
        assertEquals("Name should be equal", NEW_NAME, client.getName());
    }
}
