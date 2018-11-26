package rbtips.ui;

import java.util.Scanner;
import rbtips.dao.ArticleDao;
import rbtips.dao.Database;
import rbtips.dao.TagDao;
import rbtips.domain.AppService;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database db = new Database("jdbc:sqlite:tips.db");
        ArticleDao dao = new ArticleDao(db, "Articles");
        TagDao tagDao = new TagDao(db, "Tags");
        AppService app = new AppService(dao, tagDao);
        CommandlineIO io = new CommandlineIO(scanner);
        UserInterface ui = new UserInterface(io, app);
        ui.start();
    }
}
