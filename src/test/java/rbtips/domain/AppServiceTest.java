package rbtips.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import rbtips.dao.*;

public class AppServiceTest {

    @Before
    public void setUp() {
        Database db = new Database("jdbc:sqlite:test.db");
        ArticleDao articleDao = new ArticleDao(db, "Articles");
        TagDao tagDao = new TagDao(db, "Tags");
        AppService app = new AppService(articleDao, tagDao);
    }

}
