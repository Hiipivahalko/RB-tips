package rbtips.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rbtips.domain.*;

public class AddNewTipSceneController implements Initializable {
    
    private Stage stage;
    private Main application;
    private AppService appService;
    
    @FXML Label messageLabel;
    @FXML private TextField headline; 
    @FXML private TextField author;
    @FXML private TextField url;
    @FXML private TextArea tags;
    

    public void setApplication(Main application) {
        this.application = application;
    }
    
    public void setAppService(AppService appService) {
        this.appService = appService;
    }
    
    public void display(String title, String message, Parent root1) {
        stage = new Stage();
        stage.setTitle(title);
        messageLabel.setText(message);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.showAndWait();
    }
    
    @FXML
    private void handleAddNewTipButton(ActionEvent event) throws SQLException, IOException {
//        Article article = new Article(headline.getText(), author.getText(), url.getText());
//        String tagsForCheck = tags.getText();
        if (appService.saveArticle(headline.getText(), author.getText(), url.getText(), tags.getText())) {
            stage.close();
        }
        
    }
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}