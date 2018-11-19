package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import rbtips.domain.Article;

public class ArticleDao implements ArticleDaoApi {

    private Database db;

    @Override
    public void create(Article article) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Artikkelit(otsikko, kirjoittaja) VALUES (?, ?)");
        stmt.setString(0, article.getHeadline());
        stmt.setString(1, article.getAuthor());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

}
