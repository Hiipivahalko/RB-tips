package rbtips.domain;

import java.util.Scanner;
import rbtips.dao.Database;
import rbtips.ui.UserInterface;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database db = new Database("/database/tips.db");
        //tietokanta pitää injektoida UI:hin konstruktorin parametrina!
        UserInterface ui = new UserInterface(scanner);
        ui.start();
    }
}
