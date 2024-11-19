package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBook {
    private Book goneGirl;
    private Book theKiteRunner;
    private Book theGreatGatsby;

    @BeforeEach
    void runBefore() {
        goneGirl = new Book("Gone Girl", "Gillian Glynn", "Thriller");
        theKiteRunner = new Book("The Kite Runner", "Khaled Hosseini", "Historical fiction");
        theGreatGatsby = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Historical Fiction");

    }

    @Test
    void testBookConstructor() {
        assertEquals("Gone Girl", goneGirl.getTitle());
        assertEquals("The Kite Runner", theKiteRunner.getTitle());
        assertEquals("Khaled Hosseini", theKiteRunner.getAuthor());
        assertEquals("Historical Fiction", theGreatGatsby.getGenre());
        assertEquals(0, theGreatGatsby.getRating());
        assertFalse(goneGirl.getStatus());
    }

    @Test
    void testListRead() {
        assertEquals(false, goneGirl.getStatus());
        assertEquals(false, theGreatGatsby.getStatus());
        theKiteRunner.listRead();
        assertEquals(true, theKiteRunner.getStatus());
    }

    @Test
    void testListToBeRead() {
        assertEquals(false, goneGirl.getStatus());
        theKiteRunner.listToBeRead();
        goneGirl.listToBeRead();
        assertEquals(false, theKiteRunner.getStatus());
        assertEquals(false, goneGirl.getStatus());

    }

    // Testing for setters for test coverage
    @Test
    void testSetTitle() {
        goneGirl.setTitle("gg");
        assertEquals("gg", goneGirl.getTitle());
    }

    @Test
    void testSetAuthor() {
        goneGirl.setAuthor("ab");
        assertEquals("ab", goneGirl.getAuthor());
    }

    @Test
    void testSetGenre() {
        goneGirl.setGenre("horror");
        assertEquals("horror", goneGirl.getGenre());
    }

    @Test
    void testSetRating() {
        goneGirl.setRating(6);
        assertEquals(6, goneGirl.getRating());
    }

}
