package rbtips.dao;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import rbtips.domain.Book;

public class BookDao {

    private final Database db;
    private final String tableName;
    private final TagDao tagDao;
    private final OpenLibrary olib;

    public BookDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
        this.tagDao = new TagDao(db, "Tag");
        this.olib = new OpenLibrary();
    }

    public Book getByIsbn(String isbn) throws IOException {
        return olib.getByIsbn(isbn);
    }

    public void create(Book book) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + "(headline, author, releaseYear, isbn) VALUES (?, ?, ?, ?)");
        stmt.setString(1, book.getHeadline());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getPublishDate());
        stmt.setString(4, book.getIsbn());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public ArrayList<Book> getAll() throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Book book = new Book(rs.getString("headline"), rs.getString("author"), rs.getString("releaseYear"), rs.getString("isbn"));
            book.setDate(rs.getString("date"));
            book.setTags(String.join(",", tagDao.findBookTags(book)));
            books.add(book);
        }

        stmt.close();
        rs.close();
        conn.close();

        return books;
    }

    public void markAsRead(int bookId) throws SQLException {
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Book SET date = (?)"
                    + " WHERE id = (?)");
            stmt.setString(1, createTimeStamp());
            stmt.setInt(2, bookId);
            stmt.execute();

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String createTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String ts = sdf.format(timestamp);
        return ts;
    }

    public int getIdByHeadline(String headline) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT *  FROM " + tableName + " WHERE headline = (?)");
        stmt.setString(1, headline);
        ResultSet rs = stmt.executeQuery();

        int id = rs.getInt("id");

        stmt.close();
        rs.close();
        conn.close();
        return id;
    }

    public ArrayList<Book> searchHeadline(String headline, boolean StricSearch) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        Connection conn = db.getConnection();
        PreparedStatement stmt;
        String queryString;
        if (StricSearch) {
            stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE headline = (?) ");
            queryString = headline;
        } else {
            stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE headline LIKE (?) ");
            queryString = "%" + headline + "%";
        }

        stmt.setString(1, queryString);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Book book = new Book(rs.getString("headline"), rs.getString("author"), rs.getString("releaseYear"), rs.getString("isbn"));
            books.add(book);
        }

        stmt.close();
        rs.close();
        conn.close();

        return books;
    }

    public ArrayList<Book> searchBookByTags(String tag) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("select book.headline from book,tag,booktag"
                + " where tag.name = (?) and tag.id = booktag.tag_id and booktag.book_id = book.id");
        stmt.setString(1, tag);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String headline = rs.getString(1);
            books.add(searchHeadline(headline, true).get(0));
        }

        rs.close();
        stmt.close();
        conn.close();

        return books;
    }

    public ArrayList<Book> filterByHeadline(ArrayList<Book> oldBooks, String headlineCondition) {
        ArrayList<Book> books = new ArrayList<>();

        for (Book book : oldBooks) {
            if (book.getHeadline().toLowerCase().contains(headlineCondition.toLowerCase())) {
                books.add(book);
            }
        }
        return books;
    }

    public ArrayList<Book> filterByTags(ArrayList<Book> oldBooks, String tags) {
        if (tags.isEmpty()) {
            return oldBooks;
        }

        String[] allTags = tags.replaceAll("\\s", "").split(",");

        ArrayList<Book> books = new ArrayList<>();
        for (Book b : oldBooks) {
            String[] bookTags = b.getTags().replaceAll("\\s", "").split(",");
            for (String tag : allTags) {
                for (String bookTag : bookTags) {
                    if (bookTag.toLowerCase().equals(tag.toLowerCase())) {
                        books.add(b);
                    }
                }
            }
        }
        return books;
    }

    public void deleteBook(int bookId) {

        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            stmt.setInt(1, bookId);

            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Error Message -> " + e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
