package edu.rit.swen253.page.reddit;

import edu.rit.swen253.page.AbstractPage;
import edu.rit.swen253.utils.SeleniumUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * An {@link AbstractPage} implementation representing the reddit home page
 */
public class RedditHomePage extends AbstractPage {

    //A wait which is connected to the driver in the constructor used to validate that elements have loaded
    private WebDriverWait wait;

    private WebElement searchForm = null;
    private WebElement searchInput = null;

    /**
     * A constructor for RedditHomePage which validates the page has loaded then traverses through the shadow DOMs to
     * locate the searchForm and searchInput elements
     */
    public RedditHomePage() {
        super();

        //Grab the web driver for working with the shadow Dom
        WebDriver driver = SeleniumUtils.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            /**
             * This section of code does not follow the assignment guidelines for direct usage of DOM elements or the
             * driver but was implemented like this to best navigate through the multiple layers of shadow DOMs present
             * on reddit. This is due to getShadowRoot() returning a {@link SearchContext} which is not compatible with
             * a {@link DomElement}
             */

            // Wait for the first shadow host <reddit-search-large> to be present
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement redditSearchLarge = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("reddit-search-large")));

            // Access the shadow root of the <reddit-search-large> element
            SearchContext shadowRoot1 = redditSearchLarge.getShadowRoot();

            WebElement form = shadowRoot1.findElement(By.cssSelector("form"));

            this.searchForm = form;

            // Find <faceplate-search-input> inside the first shadow root
            WebElement faceplateSearchInput = form.findElement(By.tagName("faceplate-search-input"));

            // Access the shadow root of <faceplate-search-input>
            SearchContext shadowRoot2 = faceplateSearchInput.getShadowRoot();

            // Find the <input> element inside the second shadow root
            WebElement searchInput = shadowRoot2.findElement(By.cssSelector("input[type='text'][name='q']"));

            this.searchInput = searchInput;

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * A helper method which prints all elements inside a given shadow root.
     */
    public static void printElementsInShadowRoot(SearchContext shadowRoot1) {
        // Get all elements within the shadow root
        List<WebElement> elements = shadowRoot1.findElements(By.cssSelector("*"));  // Using XPath to select all elements

        // Print details of each element found
        for (WebElement element : elements) {
            System.out.println("Element tag: " + element.getTagName());
            System.out.println("Element HTML: " + element.getAttribute("outerHTML"));
            System.out.println("-------------");
        }
    }


    /**
     * Conducts a search if the constructor was able to successfully find the search input and form
     * @param searchText The text to search in reddit
     */
    public void ConductSearch(String searchText) {
        this.searchForm.click();
        this.searchInput.sendKeys(searchText);
        this.searchForm.submit();
    }
}
