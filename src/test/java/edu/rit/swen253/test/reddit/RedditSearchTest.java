package edu.rit.swen253.test.reddit;

import edu.rit.swen253.page.SimplePage;
import edu.rit.swen253.page.reddit.RedditHomePage;
import edu.rit.swen253.page.reddit.RedditResultsPage;
import edu.rit.swen253.test.AbstractWebTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RedditSearchTest extends AbstractWebTest {
    private RedditHomePage redditHomePage;
    private RedditResultsPage redditResultsPage;

    private final String searchText = "page object model";

    @Test
    @Order(1)
    @DisplayName("First, navigate to Reddit and find the searchbar")
    void navigateToRedditPage() {redditHomePage = navigateToPage("https://www.reddit.com/", RedditHomePage::new);}

    @Test
    @Order(2)
    @DisplayName("Second execute a search for page object model")
    void searchForPageObjectModel() {redditHomePage.ConductSearch("page object model");}

    @Test
    @Order(3)
    @DisplayName("Third, verify its a new page and interpret the results")
    void interpretResultsPage() {
        redditResultsPage = assertNewPage(RedditResultsPage::new);
    }

    @Test
    @Order(4)
    @DisplayName("Fourth and finally click the first result and validate page")
    void validatgeFirstResult() {
        //navigate to new page
        redditResultsPage.firstResultData.element().click();

        final SimplePage page = assertNewPage(SimplePage::new);
        assertAll("Page validation"
                , () -> assertEquals(redditResultsPage.firstResultData.title(), page.getTitle().substring(0, redditResultsPage.firstResultData.title().length()))
                , () -> assertEquals(redditResultsPage.firstResultData.url(), page.getURL())
        );
    }


}
