
package rbtips.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import rbtips.domain.AppService;
import rbtips.domain.Article;


public class MainPageSceneController {
    
    private Main application;
    private AppService appService;
    private ObservableList<Article> articleList;
    private AddNewTipSceneController addNew;
    
    @FXML private TableView<Article> tableView;
    
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
    private void openAddNewTipScene(ActionEvent event) throws SQLException, IOException {
        initializeAddNewTipScene();
        setArticles();
    }
    
    public void initializeAddNewTipScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddNewTipScene.fxml"));
        Parent root1 = (Parent) loader.load();
        addNew = loader.getController();
        addNew.setApplication(application);
        addNew.setAppService(appService);
        addNew.display("Add new tip!", "Input information", root1);
    }
    
    
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//    }    
    
}
