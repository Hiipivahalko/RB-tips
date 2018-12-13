
package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import rbtips.domain.Book;


public class BookDaoTest {
    
    BookDao bookDao;
    Database database;
    Book book;
    
    @Before
    public void setUp() throws Exception {
        database = new Database("jdbc:sqlite:test.db");
        bookDao = new BookDao(database, "Book");
        database.initializeDatabase();
        book = bookDao.getByIsbn("0517226952");
    }
    
    @Test
    public void rightTitle() {
        assertEquals("The ultimate hitchhiker's guide", book.getHeadline());
    }
    
    @Test
    public void rightPublish_date() {
        assertEquals("2005", book.getPublishDate());
    }
    
    @Test
    public void rightAuthors() {
        assertEquals("Douglas Adams", book.getAuthor());
    }
    
    @Test
    public void creatingBookWorks() throws Exception {
        bookDao.create(new Book("TestHeadline345", "TestAuthor12", "1902", "1234557890"));
        ArrayList<Book> books = bookDao.getAll();
        assertEquals("1902", books.get(0).getPublishDate());
        assertEquals("TestHeadline345", books.get(0).getHeadline());
    }
    
    @Test
    public void getAllWhenDatabaseIsEmpty() throws SQLException {
        ArrayList<Book> books = bookDao.getAll();
        assertTrue(books.isEmpty());
    }
    
    @Test
    public void searchByHeadlineWorks() throws SQLException {
        bookDao.create(new Book("TestHeadline345", "TestAuthor12", "1902", "1234557890"));
        ArrayList<Book> books = bookDao.searchHeadline("TestHeadline345", true);
        assertEquals("1902", books.get(0).getPublishDate());
    }
    
    @Test
    public void deletingAnExistingBookWorks() throws SQLException {
        bookDao.create(new Book("TestHeadline345", "TestAuthor12", "1902", "1234557890", "kirja"));
        bookDao.create(new Book("TestHeadline345", "TestAuthor12", "1902", "1234557890", "kirja"));
        bookDao.create(new Book("TestHeadline345", "TestAuthor12", "1902", "1234557890", "kirja"));

        bookDao.deleteBook(1);

        ArrayList<Book> books = bookDao.getAll();

        assertTrue(books.size() == 2);
    }
    
    @Test
    public void cannotDeleteAnBookThatDoesNotExist() throws SQLException {
        bookDao.create(new Book("TestHeadline345", "TestAuthor12", "1902", "1234557890", "kirja"));
        bookDao.create(new Book("TestHeadline345", "TestAuthor12", "1902", "1234557890", "kirja"));
        bookDao.create(new Book("TestHeadline345", "TestAuthor12", "1902", "1234557890", "kirja"));

        bookDao.deleteBook(5);
        
        ArrayList<Book> books = bookDao.getAll();
        
        assertTrue(books.size() == 3);
    }
    
    @Test
    public void filterHeadlineReturnsCorrectBooks() throws SQLException {
        Book a = new Book("aaa bbb ccc", "TestAuthor12", "1902", "1234557890", "kirja");
        Book a2 = new Book("aaa, eee fff", "TestAuthor12", "1902", "1234557890", "kirja");
        Book a3 = new Book("abb eee yyy", "TestAuthor12", "1902", "1234557890", "kirja");

        bookDao.create(a);
        bookDao.create(a2);
        bookDao.create(a3);

        ArrayList<Book> books = bookDao.filterByHeadline(bookDao.getAll(), "aaa");
        assertTrue(books.size() == 2);

        boolean test1 = false;
        boolean test2 = false;

        for (Book book : books) {
            if (book.getHeadline().equals(a.getHeadline())) {
                test1 = true;
            }
            if (book.getHeadline().equals(a2.getHeadline())) {
                test2 = true;
            }
        }

        assertTrue(test1 && test2);
    }
    
    @Test
    public void filterByTagsWorks() throws SQLException {
        Book a = new Book("aaa bbb ccc", "TestAuthor12", "1902", "1234557890", "book");
        Book a2 = new Book("aaa, eee fff", "TestAuthor12", "1902", "1234557890", "abc");
        Book a3 = new Book("abb eee yyy", "TestAuthor12", "1902", "1234557890", "kirja");
        
        ArrayList<Book> books = new ArrayList<>();
        books.add(a);
        books.add(a2);
        books.add(a3);
        
        books = bookDao.filterByTags(books, "book,abc");
        
        assertTrue(books.size() == 2);

    }
    
    @After
    public void tearDown() throws Exception {
        try (Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("DROP TABLE Book")) {
            statement.execute();
        }
    }
    
}
