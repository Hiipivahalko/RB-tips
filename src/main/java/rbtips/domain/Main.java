package rbtips.domain;

import java.util.Scanner;
import rbtips.dao.*;
import rbtips.ui.UserInterface;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database db = new Database("jdbc:sqlite:tips.db");
        ArticleDao dao = new ArticleDao(db);
        AppService app = new AppService(dao);
        //ylläoleva app täytyy injektoida käyttöliittymäluokalle metodien kutsumista varten
        UserInterface ui = new UserInterface(scanner, app);
        ui.start();
    }
}
