package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookTagDao {

    private final Database db;
    private final String tableName;

    public BookTagDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    public void create(int bookId, int tagId) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (book_id, tag_id) VALUES (?, ?)");

        stmt.setInt(1, bookId);
        stmt.setInt(2, tagId);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    /**
     * delete row from ArticleTags
     *
     * @param bookId
     * @param tagId
     */
    public void deleteUnions(int bookId, int tagId) {

        try (Connection conn = db.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE book_id = ? and tag_id = ?");
            stmt.setInt(1, bookId);
            stmt.setInt(2, tagId);

            stmt.execute();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Check if tag is on use on another tip, if not returns false
     *
     * @param tagId
     * @return
     */
    public boolean isThereStillMoreUnionsToTag(int tagId) {

        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE tag_id = ?");
            stmt.setInt(1, tagId);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public int getRowAmount() throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT (*) FROM " + tableName);

        ResultSet rs = stmt.executeQuery();

        int rowAmount = rs.getInt("count (*)");

        stmt.close();
        rs.close();
        conn.close();

        return rowAmount;
    }
}
