package repository_2.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookTest {
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final String TITLE = "title1";
    private static final String NEW_TITLE = "title2";
    private static final String AUTHOR = "author_name1";
    private static final String NEW_AUTHOR = "author_name2";
    private static final int PRICE = 100;
    private static final int NEW_PRICE = 200;

    private Book book;

    @Before
    public void setUp() throws Exception {
        book = new Book(TITLE, AUTHOR, PRICE);
        book.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        book = null;
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, book.getId());
    }

    @Test
    public void testSetId() throws Exception {
        book.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, book.getId());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals("Title should be equal", TITLE, book.getTitle());
    }

    @Test
    public void testSetTitle() throws Exception {
        book.setTitle(NEW_TITLE);
        assertEquals("Title should be equal", NEW_TITLE, book.getTitle());
    }

    @Test
    public void testGetAuthor() throws Exception {
        assertEquals("Author should be equal", AUTHOR, book.getAuthor());
    }

    @Test
    public void testSetAuthor() throws Exception {
        book.setAuthor(NEW_AUTHOR);
        assertEquals("Author should be equal", NEW_AUTHOR, book.getAuthor());
    }

    @Test
    public void testGetPrice() throws Exception {
        assertEquals("Price should be equal", PRICE, book.getPrice());
    }

    @Test
    public void testSetPrice() throws Exception {
        book.setPrice(NEW_PRICE);
        assertEquals("Price should be equal", NEW_PRICE, book.getPrice());
    }
}
