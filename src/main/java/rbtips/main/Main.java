package rbtips.main;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rbtips.dao.ArticleDao;
import rbtips.dao.ArticleTagDao;
import rbtips.dao.BookDao;
import rbtips.dao.BookTagDao;
import rbtips.dao.Database;
import rbtips.dao.TagDao;
import rbtips.domain.AppService;
import rbtips.ui.MainPageSceneController;

public class Main extends Application {

    private Database db;
    private ArticleDao articleDao;
    private TagDao tagDao;
    private ArticleTagDao articleTagDao;
    private AppService appService;
    private Scene mainPageScene;
    private Stage primaryStage;
    private MainPageSceneController mainPageController;
    private BookDao bookDao;
    private BookTagDao bookTagDao;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        this.db = new Database("jdbc:sqlite:tips.db");
        this.articleDao = new ArticleDao(db, "Articles");
        this.articleTagDao = new ArticleTagDao(db, "ArticleTag");
        this.tagDao = new TagDao(db, "Tag");
        this.bookDao = new BookDao(db, "Book");
        this.bookTagDao = new BookTagDao(db, "BookTag");
        this.appService = new AppService(articleDao, bookDao, tagDao, articleTagDao, bookTagDao);

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
