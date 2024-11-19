package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A class representing the To Be Read List and the Read List.
public class BookList implements Writable {
    private List<Book> readList;
    private List<Book> toBeReadList;
    // private Book book;

    // EFFECTS: initiate an empty book list
    public BookList() {
        readList = new ArrayList<>();
        toBeReadList = new ArrayList<>();
    }

    // EFFECTS: add book from toBeReadList to the readList and change the book
    // status to READ
    public void addRead(Book b) {
        if (!readList.contains(b)) {
            readList.add(b);
            b.listRead();
        }
    }

    // EFFECTS: add book to the to toBeReadList
    public void addTBR(Book b) {
        if (!toBeReadList.contains(b)) {
            EventLog.getInstance().logEvent(new Event("Added book: " + b.getTitle()));
            toBeReadList.add(b);
        }


    }

    // EFFECTS: remove book from the readList
    public void removeRead(Book b) {
        if (readList.contains(b)) {
            readList.remove(b);
        }
    }

    // EFFECTS: remove book from the toBeReadList
    public void removeTBR(Book b) {
        if (toBeReadList.contains(b)) {
            EventLog.getInstance().logEvent(new Event("Removed book: " + b.getTitle()));
            toBeReadList.remove(b);
        }
    }

    // EFFECTS: get the read list
    public List<Book> getRead() {
        return readList;
    }

    // EFFECTS: get the TBR list
    public List<Book> getTBR() {
        return toBeReadList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("toBeReadList", toBeReadToJson());
        json.put("readList", readListToJson());
        return json;
    }

    // EFFECTS: returns things in this booklist as a JSON array
    private JSONArray toBeReadToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b : toBeReadList) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns things in this booklist as a JSON array
    private JSONArray readListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b : readList) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }

}