package edu.rit.swen253.test.wikipedia;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

import edu.rit.swen253.page.SimplePage;
import edu.rit.swen253.page.wikipedia.WikipediaHomePage;
import edu.rit.swen253.page.wikipedia.WikipediaResultsPage;
import edu.rit.swen253.test.AbstractWebTest;
import edu.rit.swen253.utils.DomElement;

/**
 * A test that searches for a specific query, gathers the search results, and navigates to the first result
 * 
 * @author Cole DenBleyker
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WikipediaSearchTest extends AbstractWebTest {
    private WikipediaHomePage homePage;
    private WikipediaResultsPage resultsPage;
    
    private static final String SEARCH_QUERY = "page object model";

    @Test
    @Order(1)
    @DisplayName("First, Navigate to the Wikipedia Home page.")
    void navigateToHomePage() {
        homePage = navigateToPage("https://wikipedia.com", WikipediaHomePage::new);
    }

    @Test
    @Order(2)
    @DisplayName("Second, execute a search for page object model")
    void searchForPageObjectModel() {
        homePage.search(SEARCH_QUERY);
    }

    @Test
    @Order(3)
    @DisplayName("Third, verify its a new page and interpret the resutls")
    void interpretResultsPage() {
        resultsPage = assertNewPage(WikipediaResultsPage::new);
    }

    @Test
    @Order(4)
    @DisplayName("Lastly, click the first result and validate page")
    void validateFirstResult() {
        // Guard condition
        Assumptions.assumeTrue(resultsPage != null, "Search results page not initialized");
        Assumptions.assumeTrue(resultsPage.firstResultData != null, "No search results available");

        // Store values before navigation
        String expectedTitle = resultsPage.firstResultData.title() + " - Wikipedia";
        String expectedUrl = resultsPage.firstResultData.url();

        // Click using fresh element reference
        DomElement.findBy(By.xpath("//*[@id='mw-content-text']/div[2]/div[4]/ul/li[1]/div/div[2]/div[1]/a")).click();
        
        // Wait for page transition and validate
        homePage.waitUntilGone();
        final SimplePage page = assertNewPage(SimplePage::new);
        
        assertAll("Page Validation",
            () -> assertEquals(expectedTitle, page.getTitle()),
            () -> assertEquals(expectedUrl, page.getURL())
        );
    }
}
