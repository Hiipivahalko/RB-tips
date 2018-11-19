
package rbtips.dao;

import java.sql.Connection;
import java.sql.SQLException;
import rbtips.domain.Article;


public class ArticleDao implements ArticleDaoApi {
    
    private Database db;

    @Override
    public void create(Article article) throws SQLException {
        Connection conn = db.getConnection();
        //luodaan yhteys tietokantaan, tehdään query ja lisätään TODO
    }
    
}
