package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBookList {
    // private BookList readList;
    // private BookList toBeReadList;
    private BookList readList;
    private BookList toBeReadList;
    private Book goneGirl;
    private Book theKiteRunner;
    private Book theGreatGatsby;

    @BeforeEach
    void runBefore() {
        toBeReadList = new BookList();
        readList = new BookList();

        goneGirl = new Book("Gone Girl", "Gillian Glynn", "Thriller");
        theKiteRunner = new Book("The Kite Runner", "Khaled Hosseini", "Historical fiction");
        theGreatGatsby = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Historical Fiction");

        toBeReadList.addTBR(goneGirl);
        // tbr.addTBR(theKiteRunner);
        // tbr.addTBR(theGreatGatsby);
    }

    @Test
    void testAddRead() {
        readList.addRead(goneGirl);
        assertEquals(goneGirl, readList.getRead().get(0));
        assertEquals(true, goneGirl.getStatus());
        readList.addRead((theGreatGatsby));
        assertTrue(readList.getRead().contains(theGreatGatsby));
        assertEquals(true, theGreatGatsby.getStatus());
        readList.addRead(goneGirl); // if already contain, just return the same value
        assertTrue(readList.getRead().contains(goneGirl));

    }

    @Test
    void testAddTBR() {
        toBeReadList.addTBR(theKiteRunner);
        assertEquals(theKiteRunner, toBeReadList.getTBR().get(1));
        toBeReadList.addTBR(theGreatGatsby);
        assertEquals(theGreatGatsby, toBeReadList.getTBR().get(2));
        goneGirl.listRead();
        toBeReadList.addTBR(goneGirl);
        toBeReadList.addTBR(theKiteRunner);
        assertTrue(toBeReadList.getTBR().contains(theKiteRunner));

        toBeReadList.addTBR(theGreatGatsby);
        theGreatGatsby.listRead();
        assertTrue(toBeReadList.getTBR().contains(theGreatGatsby));
    }

    @Test
    void testRemoveRead() {
        readList.addRead(goneGirl);
        readList.addRead(theKiteRunner);
        readList.removeRead(goneGirl);
        assertEquals(theKiteRunner, readList.getRead().get(0));
        readList.removeRead(goneGirl);
        assertFalse(readList.getRead().contains(goneGirl));

    }

    @Test
    void testRemoveTBR() {
        toBeReadList.addRead(theGreatGatsby);
        toBeReadList.addRead(theKiteRunner);
        toBeReadList.removeTBR(goneGirl);
        assertFalse(readList.getRead().contains(goneGirl));
        toBeReadList.removeTBR(theGreatGatsby);
        assertEquals(theKiteRunner, toBeReadList.getRead().get(1));
        toBeReadList.removeTBR(theKiteRunner);
        assertFalse(readList.getRead().contains(theGreatGatsby));

    }

}
