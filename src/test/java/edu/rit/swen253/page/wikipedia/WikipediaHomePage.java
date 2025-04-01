package edu.rit.swen253.page.wikipedia;

import org.openqa.selenium.By;

import edu.rit.swen253.page.AbstractPage;
import edu.rit.swen253.utils.DomElement;


/**
 * An {@link AbstractPage} implementation representing the Wikipedia home page
 * 
 * @author Cole DenBleyker
 */
public class WikipediaHomePage extends AbstractPage {
    private static final By SEARCH_INPUT = By.id("searchInput");
    private static final By SEARCH_BUTTON = By.xpath("//*[@id='search-form']/fieldset/button");

    /**
     * A constructor for WikipediaHomePage which locates the search input
     */
    public WikipediaHomePage() {
        super();
        DomElement.findBy(SEARCH_INPUT);
    }

    /**
     * Searches if the constructor is able to sucessfully find the search input and form
     * 
     * @param query The search query
     */
    public void search(String query) {
        DomElement searchInput = DomElement.findBy(SEARCH_INPUT);
        searchInput.sendKeys(query);
        DomElement.findBy(SEARCH_BUTTON).click();
    }
}
