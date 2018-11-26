
package rbtips.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import rbtips.domain.*;


public interface ArticleDaoApi {
    
    void create (Article article) throws SQLException;
    ArrayList<Article> getAll() throws SQLException;
    ArrayList<Article> searchHeadline(String headline) throws SQLException;
}
