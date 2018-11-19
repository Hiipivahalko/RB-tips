package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import rbtips.domain.Article;

public class ArticleDao implements ArticleDaoApi {

    private Database db;

    public ArticleDao(Database db) {
        this.db = db;
    }

    @Override
    public void create(Article article) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Artikkelit(otsikko, kirjoittaja, url) VALUES (?, ?, ?)");
        stmt.setString(1, article.getHeadline());
        stmt.setString(2, article.getAuthor());
        stmt.setString(3, article.getUrl());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

}
