package rbtips.ui;

import java.util.Scanner;
import rbtips.dao.ArticleDao;
import rbtips.dao.Database;
import rbtips.domain.AppService;

public class UserInterface {

    private final Scanner scanner;
    private final AppService app;

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
        app.saveArticle(author, headline, url)
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
}
