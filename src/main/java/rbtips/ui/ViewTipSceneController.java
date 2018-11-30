
package rbtips.ui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import rbtips.domain.AppService;
import rbtips.domain.Article;
import rbtips.domain.Tip;
import rbtips.main.Main;

/**
 * FXML Controller class
 *
 */
public class ViewTipSceneController implements Initializable {
    
    private Main application;
    private AppService appService;
    private Stage stage;
    
    @FXML Label authorLabel;
    @FXML Label headlineLabel;
    @FXML Label urlLabel;
    @FXML Label tagsLabel;
    
    public void setApplication(Main application) {
        this.application = application;
    }
    
    public void setAppService(AppService appService) {
        this.appService = appService;
    }
    
    public void display(String title, Tip t, Parent root1) throws SQLException {
        stage = new Stage();
        stage.setTitle(title);
        authorLabel.setText(t.getAuthor());
        headlineLabel.setText(t.getHeadline());
        Article a = (Article) t;
        tagsLabel.setText(appService.getAllTagsByArticle(a));
        urlLabel.setText(a.getUrl());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.showAndWait();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
