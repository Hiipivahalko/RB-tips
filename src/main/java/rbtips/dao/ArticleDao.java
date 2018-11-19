package rbtips.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public ArrayList<Article> getAll() throws SQLException {
        ArrayList<Article> articles = new ArrayList<>();
        
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Artikkelit");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.println("täällä ollaaan");
            Article article = new Article(rs.getString("otsikko"), rs.getString("kirjoittaja"));
            System.out.println(article.getAuthor());
            articles.add(article);
        }
        
        stmt.close();
        rs.close();
        conn.close();
        
        return articles;
    }

}
