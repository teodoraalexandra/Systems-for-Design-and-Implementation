package lab9.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RentalTest {
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final int CID = 1;
    private static final int NEW_CID = 2;
    private static final int MID = 1;
    private static final int NEW_MID = 2;

    private Rental rental;

    @Before
    public void setUp() throws Exception {
        rental = new Rental(CID, MID);
        rental.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        rental = null;
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, rental.getId());
    }

    @Test
    public void testSetId() throws Exception {
        rental.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, rental.getId());
    }

    @Test
    public void testGetCID() throws Exception {
        assertEquals("CID should be equal", CID, rental.getCid());
    }

    @Test
    public void testSetCID() throws Exception {
        rental.setCid(NEW_CID);
        assertEquals("CID should be equal", NEW_CID, rental.getCid());
    }

    @Test
    public void testGetMID() throws Exception {
        assertEquals("MID should be equal", MID, rental.getMid());
    }

    @Test
    public void testSetMID() throws Exception {
        rental.setMid(NEW_MID);
        assertEquals("MID should be equal", NEW_MID, rental.getMid());
    }
}


