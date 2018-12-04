package rbtips.ui;

import static java.lang.System.exit;
import java.util.ArrayList;
import rbtips.domain.*;
import rbtips.io.IO;

public class UserInterface implements UI {

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

    @Override
    public void mainMenu() {
        String command = "";
        while (true) {
            System.out.println("\nChoose command: ");
            System.out.println("  > [n] new tip");
            System.out.println("  > [l] show tips");
            System.out.println("  > [s] search");
            System.out.println("  > [q] quit");
            command = io.nextCommand();//scanner.nextLine();
            switch (command) {
                case "n":
                    newTip();
                    break;
                case "l":
                    showTips();
                    break;
                case "s":
                    search();
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
    @Override
    public void newTip() {
        System.out.println("\nheadline: ");
        String headline = io.nextCommand();
        System.out.println("author: ");
        String author = io.nextCommand();
        System.out.println("url: ");
        String url = io.nextCommand();
        System.out.println("tag: ");
        String tags = io.nextCommand();
        app.saveArticle(headline, author, url, tags);
    }

    @Override
    public void showTips() {
        printTips(app.getAllArticles(), "no tips yet");
    }

    @Override
    public void search() {
        String command = "";
        System.out.println("  > [h] search by headline");
        System.out.println("  > [t] search by tag");
        System.out.println("  > [r] return to main menu");
        command = io.nextCommand();
        switch (command) {
            case "h":
                searchHeadline();
                break;
            case "t":
                searchTag();
                break;
            default:
                System.out.println("invalid command");
        }
    }

    public void searchHeadline() {
        String headline = "";
        System.out.println("headline:");
        headline = io.nextCommand();
        printTips(app.searchHeadline(headline), "no tips found with headline " + headline);
    }

    public void searchTag() {
        String tags = "";
        System.out.println("tags:");
        tags = io.nextCommand();
        //printTips(app.searchTag(tags), "no tips found with tags " + tags);
    }

    private void printTips(ArrayList<Article> articles, String message) {
        if (articles.isEmpty()) {
            System.out.println(message);
        } else {
            for (Article article : articles) {
                System.out.println(article);
            }
        }
    }
}
