package repository_2.domain;

import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    private static final Long IDBook = new Long(1);
    private static final Long NEW_IDBook = new Long(2);
    private static final Long IDClient = new Long(1);
    private static final Long NEW_IDClient = new Long(2);
    private static final int TRANSACTIONNUMBER = 1;
    private static final int NEW_TRANSACTIONNUMBER = 2;
    private static final String TRANSACTIONCODE = "transaction_code1";
    private static final String NEW_TRANSACTIONCODE = "transaction_code1";
    private static final String ORDERDATE = "01/01/2020";
    private static final String NEW_ORDERDATE = "02/02/2020";
    private static final Pair<Long,Long> IDTransaction  = new Pair(IDBook,IDClient);
    private static final Pair<Long,Long> NEW_IDTransaction  = new Pair(NEW_IDBook,NEW_IDClient);
    private Transaction transaction;

    @Before
    public void setUp() throws Exception {
        transaction = new Transaction(TRANSACTIONNUMBER, TRANSACTIONCODE, ORDERDATE);
        transaction.setId(IDTransaction);
    }

    @After
    public void tearDown() throws Exception {
        transaction = null;
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", IDTransaction, transaction.getId());
    }

    @Test
    public void testSetId() throws Exception {
        transaction.setId(NEW_IDTransaction);
        assertEquals("Ids should be equal", NEW_IDTransaction, transaction.getId());
    }

    @Test
    public void testGetTransactionNumber() throws Exception {
        assertEquals("TransactionNumbers should be equal", TRANSACTIONNUMBER, transaction.getTransactionNumber());
    }

    @Test
    public void testSetTransactionNumber() throws Exception {
        transaction.setTransactionNumber(NEW_TRANSACTIONNUMBER);
        assertEquals("TransactionNumbers should be equal", NEW_TRANSACTIONNUMBER, transaction.getTransactionNumber());
    }

    @Test
    public void testGetTransactionCode() throws Exception {
        assertEquals("TransactionCodes should be equal", TRANSACTIONCODE, transaction.getTransactionCode());
    }

    @Test
    public void testSetTransactionCode() throws Exception {
        transaction.setTransactionCode(NEW_TRANSACTIONCODE);
        assertEquals("TransactionCodes should be equal", NEW_TRANSACTIONCODE, transaction.getTransactionCode());
    }

    @Test
    public void testGetOrederDate() throws  Exception {
        assertEquals("OrderDates should be equal", ORDERDATE, transaction.getOrderDate());
    }

    @Test
    public void testSetOrderDate() throws Exception {
        transaction.setOrderDate(NEW_ORDERDATE);
        assertEquals("OrderDates should be equal", NEW_ORDERDATE, transaction.getOrderDate());
    }
}
