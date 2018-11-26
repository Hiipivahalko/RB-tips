package rbtips.ui;

import java.util.Scanner;
import rbtips.dao.ArticleDao;
import rbtips.dao.ArticleTagDao;
import rbtips.dao.Database;
import rbtips.dao.TagDao;
import rbtips.domain.AppService;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database db = new Database("jdbc:sqlite:tips.db");
        ArticleDao articleDao = new ArticleDao(db, "Articles");
        TagDao tagDao = new TagDao(db, "Tag");
        ArticleTagDao articleTagDao = new ArticleTagDao(db, "ArticleTag");
        
        AppService app = new AppService(articleDao, tagDao, articleTagDao);
        CommandlineIO io = new CommandlineIO(scanner);
        UserInterface ui = new UserInterface(io, app);
        ui.start();
    }
}
