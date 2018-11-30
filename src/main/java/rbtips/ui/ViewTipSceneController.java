
package rbtips.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 */
public class ViewTipSceneController implements Initializable {
    
    private Stage stage;
    
    @FXML Label authorLabel;
    @FXML Label headlineLabel;
    @FXML Label urlLabel;
    @FXML Label tagsLabel;
    
    public void display(String title, String author, String headline, String url, Parent root1) {
        stage = new Stage();
        stage.setTitle(title);
        authorLabel.setText(author);
        headlineLabel.setText(headline);
        urlLabel.setText(url);
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
