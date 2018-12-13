package rbtips.ui;

import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import rbtips.main.Main;
import rbtips.domain.*;

public class MainPageSceneController {

    private Main application;
    private AppService appService;
    private ObservableList<Tip> articleList;
    private ObservableList<Tip> bookList;
    private AddNewTipSceneController addNew;
    private ViewTipSceneController viewTip;

    @FXML
    private TableView<Tip> tableView;

    public void setApplication(Main application) {
        this.application = application;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public void setArticles() throws SQLException {
        this.articleList = FXCollections.observableArrayList(this.appService.getAllArticles());
        this.bookList = FXCollections.observableArrayList(this.appService.getAllBooks());
        this.articleList.addAll(bookList);
        tableView.setItems(articleList);
    }

    public void initializeAddNewTipScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddNewTipScene.fxml"));
        Parent root1 = (Parent) loader.load();
        addNew = loader.getController();
        addNew.setApplication(application);
        addNew.setAppService(appService);
        addNew.display("Add new tip!", "Input information", root1);
    }

    public void setViewTipScene(Tip t) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewTipScene.fxml"));
        Parent root1 = (Parent) loader.load();
        viewTip = loader.getController();
        viewTip.setApplication(application);
        viewTip.setAppService(appService);
        viewTip.setTip(t);
        viewTip.display(root1);
    }

    public void updateTimeStampColumn() throws SQLException {
        TableColumn ts = tableView.getColumns().get(2);
        tableView.getColumns().remove(ts);
        this.setArticles();
        tableView.getColumns().add(ts);
    }

}
