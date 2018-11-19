package rbtips.ui;

import static java.lang.System.exit;
import java.util.ArrayList;
import rbtips.domain.*;

public class UserInterface {

    private IO io;
    private final AppService app;

    public UserInterface(IO io, AppService app) {
        this.io = io;
        this.app = app;
    }

    public void start() {
        System.out.println("-------------------\nWelcome to RB-tips!\n-------------------\n");
        mainMenu();
    }

    private void mainMenu() {
        String command = "";
        while (true) {
            System.out.println("\nChoose command: ");
            System.out.println("  > [n] new tip");
            System.out.println("  > [s] show tips");
            System.out.println("  > [q] quit");
            command = io.nextCommand();//scanner.nextLine();
            switch (command) {
                case "n":
                    newTip();
                    break;
                case "s":
                    showTips();
                    break;
                case "q":
                    System.out.println("Shutting the program, see you later!");
                    exit(0);
                default:
                    System.out.println("invalid command");
            }
        }
    }
    
    /**
     * Creating new tip from inputs from user
     */
    private void newTip() {
        System.out.println("\nheadline: ");
        String headline = io.nextCommand();
        System.out.println("author: ");
        String author = io.nextCommand();
        System.out.println("url: ");
        String url = io.nextCommand();
        app.saveArticle(author, headline, url);
    }

    private void showTips() {
        ArrayList<Article> articles = app.getAllArticles();
        if (articles.size() > 0) {
            for (Article article : articles) {
                System.out.println(article);
            }
        } else {
            System.out.println("no tips yet");
        }
    }
}
