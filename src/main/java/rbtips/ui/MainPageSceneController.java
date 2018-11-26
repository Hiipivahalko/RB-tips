
package rbtips.ui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import rbtips.domain.AppService;
import rbtips.domain.Article;


public class MainPageSceneController {
    
    private Main application;
    private AppService appService;
    private ObservableList<Article> articleList;
    
    @FXML
    private TextField headline; 
    
    @FXML private TextField author;
    
    @FXML private TextField url;
    
    
    @FXML
    private TableView<Article> tableView;
    

    /**
     * Initializes the controller class.
     */
    
    public void setApplication(Main application) {
        this.application = application;
    }
    
    public void setAppService(AppService appService) {
        this.appService = appService;
    }
    
    public void setArticles() throws SQLException {
        this.articleList = FXCollections.observableArrayList(this.appService.getAllArticles());
        tableView.setItems(articleList);
    }
    
    @FXML
    private void handleAddNewTip(ActionEvent event) throws SQLException {
        String headLine = headline.getText();
        String a = author.getText();
        String URL = url.getText();
        
        Article article = new Article(headLine, a, URL);
        if (appService.saveArticle(headLine, a, URL)) {
            setArticles();
        }
        
    }
    
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//    }    
    
}
