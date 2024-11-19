package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import model.Book;
import model.BookList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.*;

// A class representing the GUI display
public class GUI extends JPanel implements ActionListener, WindowListener {

    private static final String add = "Add book";
    private static final String remove = "Remove book";

    private JButton addTbrButton = new JButton(add);
    private JButton addReadButton;
    private JButton removeTbrButton = new JButton(remove);
    private JButton removeReadButton;

    private JButton saveButton = new JButton("Save");
    private JButton loadButton = new JButton("Load");

    private DefaultListModel<Book> listModel = new DefaultListModel<>();
    private JList<Book> list = new JList(listModel);
    private JLabel label = new JLabel("To Be Read List");
    private JLabel box = new JLabel("New Book");
    private JLabel titleLabel = new JLabel("What's the title?");
    private JLabel authorLabel = new JLabel("What's the author?");
    private JLabel genreLabel = new JLabel("What's the genre?");

    private JFrame frame = new JFrame();

    private JTextField title;
    private JTextField author;
    private JTextField genre;

    private ImageIcon cat = new ImageIcon("cat.jpg");

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/bookList.json";
    private BookList bookList = new BookList();

    // private static EventLog log = EventLog.getInstance();

    // creates an instance of the GUI
    public GUI() {

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        textActions();
        frameActions();
        buttonActions();
        listActions();

    }

    // Adapted from
    // https://stackoverflow.com/questions/29636217/how-to-have-an-image-pop-up-in-java
    // EFFECTS: create the image
    public void image() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // this is your screen size

        ImageIcon image = new ImageIcon("minion.jpg"); // imports the image
        JLabel lbl = new JLabel(image); // puts the image into a jlabel

        f.getContentPane().add(lbl); // puts label inside the jframe

        f.setSize(image.getIconWidth(), image.getIconHeight()); // gets h and w of image and sets jframe to the size

        int x = (screenSize.width - f.getSize().width) / 2; // These two lines are the dimensions
        int y = (screenSize.height - f.getSize().height) / 2;// of the center of the screen
        f.setLocation(x, y); // sets the location of the jframe
        f.setVisible(true); // makes the jframe visible
    }

    // EFFECTS: create list actions
    public void listActions() {

        label.setBounds(110, 10, 100, 50);
        list.setBounds(50, 50, 240, 420);
        // listModel.addElement(new Book("B1", "alex", "horror"));

        box.setBounds(355, -10, 100, 50);
        titleLabel.setBounds(330, 18, 100, 40);
        authorLabel.setBounds(330, 68, 120, 40);
        genreLabel.setBounds(330, 118, 120, 40);
    }

    // EFFECTS: create frame actions
    public void frameActions() {

        // frame
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(500, 700);
        frame.setLayout(null);

        frame.addWindowListener(this);

        // add buttons to frame
        frame.add(addTbrButton);
        frame.add(removeTbrButton);
        frame.add(saveButton);
        frame.add(loadButton);
        frame.add(label);
        frame.add(box);
        frame.add(titleLabel);
        frame.add(authorLabel);
        frame.add(genreLabel);

        // add text fields
        frame.add(title);
        frame.add(author);
        frame.add(genre);

        // add list
        frame.add(list);

        frame.setVisible(true);

    }

    // EFFECTS: textfield actions
    public void textActions() {
        title = new JTextField(10);
        author = new JTextField();
        genre = new JTextField();

        title.setPreferredSize(new Dimension(20, 20));
        title.setBounds(340, 50, 90, 20);
        author.setPreferredSize(new Dimension(20, 20));
        author.setBounds(340, 100, 90, 20);
        genre.setPreferredSize(new Dimension(20, 20));
        genre.setBounds(340, 150, 90, 20);

    }

    // EFFECTS: create button actions
    public void buttonActions() {

        addTbrButton.setBounds(335, 220, 100, 30);
        addTbrButton.setFocusable(false);
        addTbrButton.addActionListener(this);

        removeTbrButton.setBounds(100, 500, 100, 50);
        removeTbrButton.setFocusable(false);
        removeTbrButton.addActionListener(this);

        saveButton.setBounds(10, 600, 80, 30);
        saveButton.setFocusable(false);
        saveButton.addActionListener(this);

        loadButton.setBounds(10, 630, 80, 30);
        loadButton.setFocusable(false);
        loadButton.addActionListener(this);
    }

    // EFFECTS: main method to run the program
    public static void main(String[] arg) {
        new GUI();
    }

    // MODIFIES: this
    // EFFECTS: perform actions after the button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {

        // add button, image pops up if succesfully added
        if (e.getSource() == addTbrButton) {

            addBook();
            image();
        }

        // remove button
        if (e.getSource() == removeTbrButton) {
            int index = list.getSelectedIndex();

            if (index != -1) {
                Book b = listModel.getElementAt(index);
                listModel.remove(index);

                bookList.removeTBR(b);

            }
        }

        // save button
        if (e.getSource() == saveButton) {
            saveBookList();
        }

        // load button
        if (e.getSource() == loadButton) {
            loadBookList();
        }
    }

    // EFFECTS: add book into list when info was entered
    public void addBook() {
        String titleA = title.getText();
        String authorA = author.getText();
        String genreA = genre.getText();

        Book newBook = new Book(titleA, authorA, genreA);
        listModel.addElement(newBook);

        bookList.addTBR(newBook);

        // clean up
        title.setText("");
        author.setText("");
        genre.setText("");
    }

    // MODIFIES: this
    // EFFECTS: saves the booklist to file
    private void saveBookList() {
        try {

            // loop for the jlist
            BookList bookList = new BookList();
            for (int i = 0; i < listModel.size(); i++) {
                bookList.addTBR(listModel.getElementAt(i));
            }

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
            BookList bookList = jsonReader.read();

            // adding for jlist
            for (Book b : bookList.getTBR()) {
                listModel.addElement(b);
            }
            // System.out.println("Loaded Book List from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: print the events out
    private static void printLog() {
        EventLog l = EventLog.getInstance();
        for (Event e : l) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'windowOpened'");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        printLog();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'windowClosed'");
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'windowIconified'");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'windowDeiconified'");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'windowActivated'");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'windowDeactivated'");
    }

}
