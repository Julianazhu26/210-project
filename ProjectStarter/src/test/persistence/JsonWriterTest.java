package persistence;

import model.BookList;
import model.Book;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            BookList bl = new BookList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            BookList bl = new BookList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBookList.json");
            writer.open();
            writer.write(bl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBookList.json");
            bl = reader.read();
            List<Book> b = bl.getTBR();
            List<Book> b2 = bl.getRead();
            
            //assertEquals("My work room", bl.getName());
            assertEquals(0, b.size());
            assertEquals(0, b2.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            BookList bl = new BookList();
            bl.addTBR(new Book("a", "b", "c"));
            bl.addRead(new Book("a", "b", "c"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBooklist.json");
            writer.open();
            writer.write(bl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBooklist.json");
            bl = reader.read();
            
            List<Book> readList = bl.getRead();
            List<Book> readTBR = bl.getTBR();
            
            assertEquals(1, readList.size());
            assertEquals(1, readTBR.size());
            checkBook("a", "b", "c", 0, true, readList.get(0));
            checkBook("a", "b", "c", 0, false, readTBR.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}