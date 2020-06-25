package lab9.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovieTest {
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final String SERIAL_NUMBER = "serial_number_1";
    private static final String NEW_SERIAL_NUMBER = "serial_number_2";
    private static final String TITLE = "title1";
    private static final String NEW_TITLE = "title2";
    private static final String DIRECTOR = "director1";
    private static final String NEW_DIRECTOR = "director2";
    private static final int DURATION = 100;
    private static final int NEW_DURATION = 200;

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        movie = new Movie(SERIAL_NUMBER, TITLE, DIRECTOR, DURATION);
        movie.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        movie = null;
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, movie.getId());
    }

    @Test
    public void testSetId() throws Exception {
        movie.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, movie.getId());
    }

    @Test
    public void testGetSerialNumber() throws Exception {
        assertEquals("Serial number should be equal", SERIAL_NUMBER, movie.getSerialNumber());
    }

    @Test
    public void testSetSerialNumber() throws Exception {
        movie.setSerialNumber(NEW_SERIAL_NUMBER);
        assertEquals("Serial number should be equal", NEW_SERIAL_NUMBER, movie.getSerialNumber());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals("Title should be equal", TITLE, movie.getTitle());
    }

    @Test
    public void testSetTitle() throws Exception {
        movie.setTitle(NEW_TITLE);
        assertEquals("Title should be equal", NEW_TITLE, movie.getTitle());
    }

    @Test
    public void testGetDirector() throws Exception {
        assertEquals("Director should be equal", DIRECTOR, movie.getDirector());
    }

    @Test
    public void testSetDirector() throws Exception {
        movie.setDirector(NEW_DIRECTOR);
        assertEquals("Director should be equal", NEW_DIRECTOR, movie.getDirector());
    }

    @Test
    public void testGetDuration() throws Exception {
        assertEquals("Duration should be equal", DURATION, movie.getDuration());
    }

    @Test
    public void testSetDuration() throws Exception {
        movie.setDuration(NEW_DURATION);
        assertEquals("Duration should be equal", NEW_DURATION, movie.getDuration());
    }
}

