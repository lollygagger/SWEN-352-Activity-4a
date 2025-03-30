package edu.rit.swen253.page.reddit;

import edu.rit.swen253.page.AbstractPage;
import edu.rit.swen253.utils.DomElement;
import org.openqa.selenium.By;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * An {@link AbstractPage} implementation representing the results page after conducting a search on reddit
 */
public class RedditResultsPage extends AbstractPage {

    private static final By MAIN_CONTENT_FINDER = By.xpath("//*[@id=\"subgrid-container\"]");

    public firstResultRecord firstResultData;

    /**
     * A constructor for RedditResultsPage which locates the results, prints them as they are found and populates a
     * {@link firstResultRecord} object
     */
    public RedditResultsPage() {
        super();
        DomElement mainContent = findOnPage(MAIN_CONTENT_FINDER);
        List<DomElement> results = mainContent.findChildrenBy(By.cssSelector("a[data-testid='post-title']"));

        boolean firstResult = true;

        for (DomElement result : results) {
            try {
                String url = result.getAttribute("href");

                String title = result.getText();

                // Print the URL and title
                System.out.println("Title: " + title);
                System.out.println("URL: " + url);
                System.out.println("------");

                if (firstResult) {
                    firstResult = false;
                    this.firstResultData = new firstResultRecord(title, url, result);
                }

            } catch (Exception e) {
                System.out.println("Error extracting information from a result.");
                fail(e.getMessage());
            }
        }
    }

    /**
     * A Record for storing the data of the first result found
     * @param title the title of the first search result
     * @param url the url of the first search result
     * @param element the {@link DomElement} of the first result
     */
    public record firstResultRecord(String title, String url, DomElement element) {}



}
