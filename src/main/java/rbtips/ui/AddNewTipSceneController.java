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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rbtips.main.Main;
import rbtips.domain.*;

public class AddNewTipSceneController implements Initializable {

    private Stage stage;
    private Main application;
    private AppService appService;

    @FXML
    VBox vbox;
    @FXML
    ComboBox selectBox;
    @FXML
    Label messageLabel;
    @FXML
    private TextField headline;
    @FXML
    private TextField author;
    @FXML
    private TextField url;
    @FXML
    private TextArea tags;
    @FXML
    private TextField isbn;
    @FXML
    private TextField publicationYear;

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
        if (selectBox.getValue().equals("Article")) {
            if (appService.saveArticle(headline.getText(), author.getText(), url.getText(), tags.getText())) {
                stage.close();
            }
        } else if(selectBox.getValue().equals("Book")) {
            if(appService.saveBook(headline.getText(), author.getText(), publicationYear.getText(), isbn.getText(), tags.getText())) {
                stage.close();
            }
        }

    }
    
    @FXML
    private void populate(ActionEvent event) throws IOException {
        if(selectBox.getValue().equals("Book")) {
            try {
                Book b = appService.getBookByIsbn(isbn.getText());
                headline.setText(b.getHeadline());
                author.setText(b.getAuthor());
                publicationYear.setText(b.getPublishDate());
            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectBox.getItems().addAll("Article", "Book");
        selectBox.getSelectionModel().selectFirst();
        setIsbnAndPublicationYearAndURLFields();
    }

    @FXML
    private void changeView() {
        if (selectBox.getValue().equals("Book")) {
            System.out.println("book chosen");
            setIsbnAndPublicationYearAndURLFields();
        }
        if (selectBox.getValue().equals("Article")) {
            System.out.println("article chosen");
            setIsbnAndPublicationYearAndURLFields();
        }
    }

    private void setIsbnAndPublicationYearAndURLFields() {
        if (!isbn.getText().isEmpty() || !publicationYear.getText().isEmpty() || !url.getText().isEmpty()) {
            isbn.setText("");
            publicationYear.setText("");
            url.setText("");
        }
        if (selectBox.getValue().equals("Book")) {
            isbn.setEditable(true);
            isbn.setMouseTransparent(false);
            isbn.setFocusTraversable(true);
            publicationYear.setEditable(true);
            publicationYear.setMouseTransparent(false);
            publicationYear.setFocusTraversable(true);
            url.setEditable(false);
            url.setMouseTransparent(true);
            url.setFocusTraversable(false);
        } else {
            isbn.setEditable(false);
            isbn.setMouseTransparent(true);
            isbn.setFocusTraversable(false);
            publicationYear.setEditable(false);
            publicationYear.setMouseTransparent(true);
            publicationYear.setFocusTraversable(false);
            url.setEditable(true);
            url.setMouseTransparent(false);
            url.setFocusTraversable(true);
        }
    }

}