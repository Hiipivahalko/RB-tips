package rbtips.domain;

import java.util.Scanner;
import rbtips.dao.ArticleDao;
import rbtips.dao.Database;
import rbtips.ui.UserInterface;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database db = new Database("/database/tips.db");
        ArticleDao dao = new ArticleDao(db);
        //ylläoleva dao täytyy injektoida käyttöliittymä/sovelluslogiikkaluokalle
        UserInterface ui = new UserInterface(scanner);
        ui.start();
    }
}
