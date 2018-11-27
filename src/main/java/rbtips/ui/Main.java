package rbtips.ui;

import java.util.ArrayList;
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
        ArticleTagDao articleTagDao = new ArticleTagDao(db, "ArticleTag");
        TagDao tagDao = new TagDao(db, "Tag");

        AppService app = new AppService(articleDao, tagDao, articleTagDao);
        CommandlineIO io = new CommandlineIO(scanner);
        UserInterface ui = new UserInterface(io, app);
        ui.start();
    }
}
