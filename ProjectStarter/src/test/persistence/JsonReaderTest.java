package persistence;

import model.BookList;
import model.Book;
import org.junit.jupiter.api.Test;

import java.io.IOException;
// import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BookList bl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBookList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBookList.json");
        try {
            BookList bl = reader.read();
            // assertEquals("toBeReadList", bl.getName());
            List<Book> b = bl.getTBR();
            List<Book> b2 = bl.getRead();
            assertEquals(0, b.size());
            assertEquals(0, b2.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBookList() {
        JsonReader reader = new JsonReader("./data/testReaderNoEmptyBookList.json");
        try {
            BookList bl = reader.read();
            // assertEquals("My work room", bl.getName());
            List<Book> b = bl.getTBR();
            List<Book> b2 = bl.getRead();

            assertEquals(2, b.size());
            assertEquals(2, b2.size());

            checkBook("a", "b", "c", 0, false, b.get(0));
            checkBook("c", "b", "a", 0, false, b.get(1));
            checkBook("1", "2", "3", 0, true, b2.get(0));
            checkBook("3", "2", "1", 0, true, b2.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}