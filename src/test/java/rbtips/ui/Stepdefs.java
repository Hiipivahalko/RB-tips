package rbtips.ui;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import java.util.List;
import rbtips.dao.ArticleDao;
import rbtips.dao.Database;
import rbtips.domain.AppService;

import static org.junit.Assert.*;
import rbtips.domain.Article;

public class Stepdefs {
    
    Database db = new Database("jdbc:sqlite:testDb.db");
    ArticleDao dao = new ArticleDao(db);
    AppService app = new AppService(dao);
    IOStub io = new IOStub(new String[1]);
    UIStub ui = new UIStub(io, app);
    
    
    @Given("^new tip command is given$")
    public void new_tip_command_is_given() throws Throwable {
        
    }

    @When("^a valid headline \"([^\"]*)\" and valid author \"([^\"]*)\" and valid url \"([^\"]*)\"$")
    public void a_valid_headline_and_valid_author_and_valid_url(String headline, String author, String url) throws Throwable {
        io.setCommands(headline, author, url);
        ui.newTip();
    }

    @Then("^artile, which headline is \"([^\"]*)\" and author is \"([^\"]*)\" and url is \"([^\"]*)\" is found$")
    public void artile_which_headline_is_and_author_is_and_url_is_is_found(String headline, String author, String url) throws Throwable {
        assertTrue(searchArticles(headline, author, url));
        
    }
    
    
    
    private boolean searchArticles(String headline, String author, String url) {
        ArrayList<Article> allArticlesFromDb = app.getAllArticles();
        
        for (Article article : allArticlesFromDb) {
            if (article.getHeadline().equals(headline) &&
                    article.getAuthor().equals(author) &&
                    article.getUrl().equals(url)) {
                return true;
            }
        }
        
        return false;
    }
  
}
