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
    TagDao tagDao = new TagDao(db, "Tag");
    AppService app = new AppService(dao, tagDao, articleTagDao);
    IOStub io = new IOStub(new String[1]);
    UIStub ui = new UIStub(io, app);

    @Before
    public void initializeDatabase() {
        db.initializeDatabase();
    }

    @Given("^User clicks the button Add new tip$")
    public void new_tip_command_is_given() throws Throwable {

    }

    @When("^headline \"([^\"]*)\" and author \"([^\"]*)\" and url \"([^\"]*)\" are entered$")
    public void a_valid_headline_and_valid_author_and_valid_url(String headline, String author, String url) throws Throwable {
        io.setCommands(headline, author, url);
        ui.newTip();
    }

    @Then("^artile with headline \"([^\"]*)\" and author \"([^\"]*)\" and url \"([^\"]*)\" is found$")
    public void artile_which_headline_is_and_author_is_and_url_is_is_found(String headline, String author, String url) throws Throwable {
        assertTrue(searchArticles(headline, author, url));

    }

    @Then("^Article with headline \"([^\"]*)\" and author \"([^\"]*)\" and url \"([^\"]*)\" is not saved to database because headline is too short$")
    public void article_is_not_saved_to_database_and_with_invalid_headline_and_valid_author_url_input(String headline, String author, String url) throws Throwable {
        assertTrue(!searchArticles(headline, author, url));
    }

    @Given("^Database is initialized$")
    public void database_is_initialized() throws Throwable {
        assertTrue(app.getAllArticles().size() == 0);
    }

    @When("^User adds one tip to database$")
    public void user_add_one_tip_to_database_and_execute_command_list_tips() throws Throwable {
        a_valid_headline_and_valid_author_and_valid_url("otsikko", "tekijä", "www.blog.fi");
    }

    @Then("^User sees all tips from database in the GUI and the list size is (\\d+)$")
    public void user_see_all_tips_from_database_count_is(int val) throws Throwable {
        assertTrue(app.getAllArticles().size() == val);
    }

    @Given("^a valid blog is saved with headline \"([^\"]*)\"")
    public void valid_blog_is_saved(String headline) throws Throwable {
        io.setCommands(headline, "author", "www.blog.fi");
        ui.newTip();
    }

    @When("^Command search is given")
    public void command_search_is_given() throws Throwable {

    }

    @When("^Command search by headline is given")
    public void command_search_by_headline_is_given() throws Throwable {

    }

    @Then("^Article with headline \"([^\"]*)\" is found")
    public void article_with_headline_is_found(String headline) throws Throwable {
        assertTrue(app.searchHeadline(headline).size() == 1);
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

    @Given("^a valid article is saved with tag \"([^\"]*)\"")
    public void valid_article_is_saved(String tag) throws Throwable {
        io.setCommands("headline", "author", "www.article.com", tag);
        ui.newTip();
    }

    @When("^Command search by tags is given")
    public void command_search_by_tags_is_given() throws Throwable {

    }

    @Then("^Article with tag \"([^\"]*)\" is found")
    public void article_with_tag_is_found(String tag) throws Throwable {
        assertTrue(app.searchTag(tag).size() == 1);
    }

    @Then("^Articles are not found when searched with tag \"([^\"]*)\"$")
    public void articles_are_not_found_when_searched_with_tag(String tag) throws Throwable {
        assertTrue(app.searchTag(tag).size() == 0);
    }

    @Given("^a valid article is saved with the tag \"([^\"]*)\"$")
    public void a_valid_article_is_saved_with_the_tag(String tag) throws Throwable {
        io.setCommands("ai is going to destroy the world", "janne ahonen", "www.article.fi", tag);
        ui.newTip();
    }

    @Then("^Two articles are found when searched with tag \"([^\"]*)\"$")
    public void two_articles_are_found_when_searched_with_tag(String tags) throws Throwable {
        assertTrue(app.searchTag(tags).size() == 2);
    }

}
