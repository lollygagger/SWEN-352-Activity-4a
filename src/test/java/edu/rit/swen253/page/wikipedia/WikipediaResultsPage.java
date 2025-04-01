package edu.rit.swen253.page.wikipedia;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.openqa.selenium.By;

import edu.rit.swen253.page.AbstractPage;
import edu.rit.swen253.utils.DomElement;

/**
 * An {@link AbstractPage} implementation that represents the result page after searching for
 * a topic on Wikipedia that does *NOT* have an auto-redirect page
 * 
 * @author Cole DenBleyker
 */
public class WikipediaResultsPage extends AbstractPage {
    private static final By RESULTS_CONTAINER = By.xpath("//*[@id='mw-content-text']/div[2]/div[4]");
    private static final By RESULT_ITEMS = By.xpath(".//ul/li[position() >= 1 and position() <= 20]");
    
    public firstResultRecord firstResultData;

    /**
     * A constructor for WikipediaResultsPage which locates the results, prints them as they are found and
     * populates a {@link firstResultRecord} object
     */
    public WikipediaResultsPage() {
        super();
        DomElement resultsContainer = findOnPage(RESULTS_CONTAINER);
        List<DomElement> results = resultsContainer.findChildrenBy(RESULT_ITEMS);

        boolean isFirst = true;
        
        for (DomElement result : results) {
            try {
                DomElement titleElement = result.findChildBy(
                    By.xpath(".//div/div[2]/div[1]/a")
                );
                String title = titleElement.getText();
                String url = titleElement.getAttribute("href");

                // Print the URL and title
                System.out.println("Title: " + title);
                System.out.println("URL: " + url);
                System.out.println("------");

                if (isFirst) {
                    this.firstResultData = new firstResultRecord(title, url, titleElement);
                    isFirst = false;
                }
            } catch (Exception e) {
                System.out.println("Error processing result");
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
