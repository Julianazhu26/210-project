package ui;

import model.Book;
import model.BookList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

// import java.util.ArrayList;
// import java.util.List;
import java.util.Scanner;

import javax.swing.JPopupMenu;

import java.io.FileNotFoundException;
import java.io.IOException;

// // A class representing the ui application of the reading lists.
public class BookApp {
    // private List<Book> readList;
    // private List<Book> toBeReadList;
    private BookList bookList;
    private Scanner scanner;
    private boolean isProgramRunning;

    private static final String JSON_STORE = "./data/bookList.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // private static EventLog log = EventLog.getInstance();

    // EFFECTS: creates an instance of the ReadingList ui
    public BookApp() throws FileNotFoundException {
        init();

        bookList = new BookList();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        while (this.isProgramRunning) {
            handleMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the ui with starting values
    public void init() {

        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;

    }

    // EFFECTS: prints out a line of dashes to act as a divider
    private void printDivider() {
        System.out.println("------------------------------------");
    }

    // EFFECTS: disaplys main menu and process inputs
    public void handleMenu() {
        menu();
        String input = this.scanner.nextLine();
        handleMain(input);
    }

    // EFFECTS: main menu options
    public void menu() {
        System.out.println("Please select an option:\n");
        System.out.println("1: add book to To Be Read List");
        System.out.println("2: add book to Read List");
        System.out.println("3: view To Be Read List");
        System.out.println("4: view Read List");
        System.out.println("5: Save Book List");
        System.out.println("6: Load Book List");
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: process the commands for main menu
    public void handleMain(String num) {
        switch (num) {
            // 1 asks for adding TBR
            case "1":
                addBookTBR();
                break;

            // 2 asks for adding Read List
            case "2":
                addBookRead();
                break;

            // 3 asks printing out TBR
            case "3":
                viewTBR(bookList);
                break;

            // 4 asks printing out Read
            case "4":
                viewRead(bookList);
                break;

            case "5":
                saveBookList();
                break;

            case "6":
                loadBookList();
                break;

        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new book to TBR
    public void addBookTBR() {
        System.out.println("What's the title of the book?");
        String title = this.scanner.nextLine();
        System.out.println("What's the author of the book?");
        String author = this.scanner.nextLine();
        System.out.println("What's the genre of the book?");
        String genre = this.scanner.nextLine();

        Book book = new Book(title, author, genre);
        bookList.getTBR().add(book);
        System.out.println(book.getTitle() + " was added to your To Be Read List.");

    }

    // MODIFIES: this
    // EFFECTS: adds a new book to ReadList
    public void addBookRead() {
        System.out.println("What's the title of the book?");
        String title = this.scanner.nextLine();
        System.out.println("What's the author of the book?");
        String author = this.scanner.nextLine();
        System.out.println("What's the genre of the book?");
        String genre = this.scanner.nextLine();

        Book book = new Book(title, author, genre);
        bookList.getRead().add(book);
        System.out.println(book.getTitle() + " was added to your Read List.");

    }

    // EFFECTS: view all books within the readList
    public void viewRead(BookList bl) {
        if (bl.getRead().isEmpty() == false) {
            for (Book b : bl.getRead()) {
                System.out.println(b.getTitle());
            }
        } else {
            System.out.println("Sorry the Read List is empty");
            handleMenu();
        }
    }

    // EFFECTS: view all books within the toBeReadList
    public void viewTBR(BookList bl) {
        if (bl.getTBR().isEmpty() == false) {
            for (Book b : bl.getTBR()) {
                System.out.println(b.getTitle());

            }
        } else {
            System.out.println("Sorry the To Be Read List is empty");
            handleMenu();
        }

        tbrMenu();
        printDivider();
        String input = this.scanner.nextLine();
        handleTBR(input);

    }

    // MODIFIES THIS
    // EFFECTS: give rating to the books in TBR
    public void addRating() {
        System.out.println("Which book would you like to add ratings to?");
        String input = this.scanner.nextLine();
        for (Book b : bookList.getTBR()) {
            if (b.getTitle().equals(input)) {
                System.out.println("What rating do you want to give out of 10?(must be integer)");
                int newRating;
                newRating = this.scanner.nextInt();
                b.setRating(newRating);
                System.out.println("Wonderful! You've rated " + input + " a " + newRating + " out of 10");
                return;
            }
        }
    }

    // EFFECTS: display menu for ToBeReadList
    public void tbrMenu() {
        System.out.println("a: to remove a book");
        System.out.println("b: to give rating");
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: process the commands for TBR menu
    public void handleTBR(String num) {
        switch (num) {
            // 1 remove a book
            case "a":
                System.out.println("Which book do you want to remove?");
                String input = this.scanner.nextLine();
                for (Book b : bookList.getTBR()) {
                    if (b.getTitle().equals(input)) {
                        bookList.getTBR().remove(b);
                        System.out.println("You've removed " + input + " from the list.");
                        printDivider();
                        handleMenu();
                    }
                }
                break;

            // 2 give rating
            case "b":
                addRating();
                break;

        }
    }
    

    // EFFECTS: saves the booklist to file
    private void saveBookList() {
        try {
            jsonWriter.open();
            jsonWriter.write(bookList);
            jsonWriter.close();
            System.out.println("Saved Book List to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads booklist from file
    private void loadBookList() {
        try {
            bookList = jsonReader.read();
            System.out.println("Loaded Book List from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // // EFFECTS: print the events out
    // private static void print() {
    //   for (Event e : log) {
    //     System.out.println(e.getDescription());
    //   }  
    // }

}