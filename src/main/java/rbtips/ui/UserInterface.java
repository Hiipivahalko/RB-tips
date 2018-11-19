package rbtips.ui;

import java.util.ArrayList;
import java.util.Scanner;
import rbtips.domain.Article;

public class UserInterface {

    private final Scanner scanner;
    private final ArrayList<Article> articles;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
        this.articles = new ArrayList<>();
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
        articles.add(new Article(headline, author, url));
    }

    private void showTips() {
        if (articles.size() > 0) {
            articles.forEach((article) -> {
                System.out.println(article.getAuthor() + ": " + article.getHeadline());
            });
        } else {
            System.out.println("no tips yet");
        }
    }
}
