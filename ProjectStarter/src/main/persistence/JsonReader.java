package persistence;

import model.Book;
import model.BookList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// CITATION: model this based on sample application JsonSerializationDemo

// A class that acts as a reader that reads Book Lists from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Book Lists from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BookList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBookList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses book lists from JSON object and returns it
    private BookList parseBookList(JSONObject jsonObject) {
        // String name = jsonObject.getString("name");
        BookList bl = new BookList();
        addTBR(bl, jsonObject);
        addRead(bl, jsonObject);
        return bl;
    }

    // MODIFIES: bl
    // EFFECTS: parses ToBeReadList frxom JSON object and adds them to workroom
    private void addTBR(BookList bl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("toBeReadList");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(bl, nextBook);
        }
    }

    // MODIFIES: bl
    // EFFECTS: parses ReadList from JSON object and adds them to workroom
    private void addRead(BookList bl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("readList");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(bl, nextBook);
        }
    }

    // MODIFIES: bl
    // EFFECTS: parses books from JSON object and adds it to booklist
    private void addBook(BookList bl, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        String genre = jsonObject.getString("genre");
        int rating = jsonObject.getInt("rating");
        Boolean status = jsonObject.getBoolean("status");

        Book book = new Book(title, author, genre);
        if (status) {
            bl.addRead(book);
        } else {
            bl.addTBR(book);
        }
    }
}
