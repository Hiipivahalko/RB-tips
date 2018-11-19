
package rbtips.dao;

import java.sql.SQLException;
import rbtips.domain.*;


public interface ArticleDaoApi {
    
    void create (Article article) throws SQLException;
    
}
