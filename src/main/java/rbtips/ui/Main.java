package rbtips.ui;

import java.sql.SQLException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rbtips.dao.ArticleDao;
import rbtips.dao.Database;
import rbtips.domain.AppService;

public class Main extends Application {
    
    private Database db;
    private ArticleDao articleDao;
    private AppService appService;
    private Scene mainPageScene;
    private Stage primaryStage;
    private MainPageSceneController mainPageController;

    public static void main(String[] args) {
        launch(args);
//        Scanner scanner = new Scanner(System.in);
//        AppService app = new AppService(articleDao);
//        CommandlineIO io = new CommandlineIO(scanner);
//        UserInterface ui = new UserInterface(io, app);
//        ui.start();
    }
    
    @Override
    public void init() throws Exception {
        this.db = new Database("jdbc:sqlite:tips.db");
        this.articleDao = new ArticleDao(db, "Articles");
        this.appService = new AppService(articleDao);
        
        FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/fxml/MainPageScene.fxml"));
        Parent mainPagePane = mainPageLoader.load();
        mainPageController = mainPageLoader.getController();
        mainPageController.setAppService(appService);
        mainPageController.setApplication(this);
        mainPageScene = new Scene(mainPagePane);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("RB-Tips");
        setMainPage();
        primaryStage.show();
    }
    
    public void setMainPage() throws SQLException {
        primaryStage.setScene(mainPageScene);
        mainPageController.setArticles();
    }
    
    
}
