package rbtips.ui;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import java.util.List;
import rbtips.dao.ArticleDao;
import rbtips.dao.Database;
import rbtips.dao.TagDao;
import rbtips.domain.AppService;

import static org.junit.Assert.*;
import rbtips.dao.ArticleTagDao;
import rbtips.domain.Article;

public class Stepdefs {

    Database db = new Database("jdbc:sqlite:testDb.db");
    ArticleDao dao = new ArticleDao(db, "Articles");
    ArticleTagDao articleTagDao = new ArticleTagDao(db, "ArticleTag");
    TagDao tagDao = new TagDao(db, articleTagDao, "Tag");
    AppService app = new AppService(dao, tagDao, articleTagDao);
    IOStub io = new IOStub(new String[1]);
    UIStub ui = new UIStub(io, app);

    @Before
    public void initializeDatabase() {
        db.initializeDatabase();
    }

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

    @Given("^new tip command is given, and invalid headline \"([^\"]*)\" with valid author \"([^\"]*)\" and url \"([^\"]*)\"$")
    public void new_tip_command_is_given_and_invalid_headline_with_valid_author_and_url(String headline, String author, String url) throws Throwable {
        io.setCommands(headline, author, url);
        ui.newTip();
    }

    @Then("^Article is not saved to database and with invalid headline \"([^\"]*)\" and valid author \"([^\"]*)\", url \"([^\"]*)\" input$")
    public void article_is_not_saved_to_database_and_with_invalid_headline_and_valid_author_url_input(String headline, String author, String url) throws Throwable {
        assertTrue(!searchArticles(headline, author, url));
    }

    @Given("^Database is initialized$")
    public void database_is_initialized() throws Throwable {
        db.initializeDatabase();
        assertTrue(app.getAllArticles().size() == 0);
    }

    @When("^User add one tip to database and execute command list tips$")
    public void user_add_one_tip_to_database_and_execute_command_list_tips() throws Throwable {
        new_tip_command_is_given_and_invalid_headline_with_valid_author_and_url("otsikko", "tekijä", "www.blog.fi");
    }

    @Then("^User see all tips from database, count is \"([^\"]*)\"$")
    public void user_see_all_tips_from_database_count_is(String count) throws Throwable {
        assertTrue(app.getAllArticles().size() == 1);
    }

    private boolean searchArticles(String headline, String author, String url) {
        ArrayList<Article> allArticlesFromDb = app.getAllArticles();

        for (Article article : allArticlesFromDb) {
            if (article.getHeadline().equals(headline)
                    && article.getAuthor().equals(author)
                    && article.getUrl().equals(url)) {
                return true;
            }
        }

        return false;
    }

}
