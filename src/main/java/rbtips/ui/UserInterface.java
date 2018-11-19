package rbtips.ui;

import java.util.Scanner;
import rbtips.dao.ArticleDao;
import rbtips.dao.Database;
import rbtips.domain.AppService;

public class UserInterface {

    private final Scanner scanner;
    private AppService app;

    public UserInterface(Scanner scanner, AppService app) {
        this.scanner = scanner;
        this.app = app;
    }

    public void start() {
        System.out.println("Welcome to RB-tips!");
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            System.out.println("Choose command: ");
            System.out.println("new tip");
            System.out.println("show tips");

            String command = scanner.nextLine();
            switch (command) {
                case "new tip":
                    newTip();
                    break;
                case "show tips":
                    showTips();
                    break;
                default:
                    System.out.println("invalid command");
            }
        }
    }

    private void newTip() {
        System.out.println("headline: ");
        String headline = scanner.nextLine();
        System.out.println("author: ");
        String author = scanner.nextLine();
        System.out.println("url: ");
        String url = scanner.nextLine();
    }

    private void showTips() {
        /*if (articles.size() > 0) {
            articles.forEach((article) -> {
                System.out.println(article.getAuthor() + ": " + article.getHeadline());
            });
        } else {
            System.out.println("no tips yet");
        }*/
    }

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
