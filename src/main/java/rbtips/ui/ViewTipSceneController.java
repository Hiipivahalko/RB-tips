package rbtips.ui;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import rbtips.domain.AppService;
import rbtips.domain.Article;
import rbtips.domain.Book;
import rbtips.domain.Tip;
import rbtips.main.Main;

/**
 * FXML Controller class
 *
 */
public class ViewTipSceneController {

    private Main application;
    private AppService appService;
    private Stage stage;
    private Tip tip;

    @FXML HBox hbox;
    @FXML VBox vbox;
    @FXML Label authorLabel;
    @FXML Label headlineLabel;
    @FXML Label timeStampLabel;
    @FXML Hyperlink url;
    @FXML Label tagsLabel;
    @FXML Label bookLabel;
    @FXML Button markReadButton;
    @FXML Button deleteTipButton;

    public void setApplication(Main application) {
        this.application = application;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    //TODO siisti metodi
    public void display(Parent root1) throws SQLException {
        stage = new Stage();
        setTipInformation();
        url.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    new ProcessBuilder("x-www-browser", url.getText()).start();
                } catch (IOException e) {
                    try {
                        String url_open = url.getText();
                        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));

                    } catch (Exception ex) {

                    }
                }
            }
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root1));
        stage.showAndWait();
    }

    public void openWinBrowser() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(url.getText()));
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void openMacBrowser() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("xdg-open " + url.getText());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Set information of Tip, when user wants to view the tip
     *
     * @throws SQLException
     */
    private void setTipInformation() throws SQLException {
        stage.setTitle(tip.getAuthor() + ", " + tip.getHeadline());
        authorLabel.setText("Author: " + tip.getAuthor());
        
        if (tip.getType() == 0) {
            Article a = (Article) tip;
            headlineLabel.setText("Headline: " + tip.getHeadline());
            tagsLabel.setText("Tags: " + appService.getAllTagsByArticle(a));
            url.setText(a.getUrl());
            timeStampLabel.setText("Marked as read: " + a.getDate());
            vbox.getChildren().remove(bookLabel);
            checkMarkedStatus();
        } else {
            Book b = (Book) tip;
            headlineLabel.setText("Title: " + tip.getHeadline());
            tagsLabel.setText(appService.getAllTagsByBook(b));
            bookLabel.setText("ISBN: " + b.getIsbn() + ", publication year:" + b.getPublishDate());
            timeStampLabel.setText(b.getDate());
            vbox.getChildren().remove(url);
            checkMarkedStatus();
        }

    }

//    @FXML
//    public void handleClickLink(ActionEvent event) throws Exception {
//        try {
//            Desktop.getDesktop().browse(new URL(url.getText()).toURI());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Delete Tip, button handler. Delete tip from database and software
     *
     * @param actionEvent
     */
    @FXML
    public void handleTipDeleteButton(ActionEvent actionEvent) {
        if(tip.getType() == 0) {
            appService.deleteTip((Article)tip);
        } else {
            appService.deleteBook((Book)tip);
        }
        
        stage.close();
    }
    
    @FXML
    public void handleMarkReadButton(ActionEvent actionEvent) throws SQLException {
        appService.markAsRead(tip);
        stage.close();
    }
    
    public void checkMarkedStatus() {
        if(tip.getDate() != null) {
            hbox.getChildren().remove(markReadButton);
        }
    }

}
