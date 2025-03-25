package edu.rit.swen253.page.sample;

import edu.rit.swen253.page.AbstractPage;
import edu.rit.swen253.utils.DomElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;


/**
 * A Page Object for the RIT Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class RitHomePage extends AbstractPage {

  /**
   * A finder to extract the main body content.
   * WARN: this is fragile code
   */
  private static final By MAIN_CONTENT_FINDER = By.cssSelector("main > div > div.field--name-field-content");

  private DomElement mainContentPanel;

  public RitHomePage() {
    super();
    // validate basic page structure
    try {
      mainContentPanel = findOnPage(MAIN_CONTENT_FINDER);
    } catch (TimeoutException e) {
      fail("Main content panel not found");
    }
  }

  public List<RitAreaOfStudyLink> getStudyLinks() {
    // the Ratings panel is in the fifth <div> of the main content
    // WARN: this is fragile code
    return mainContentPanel.findChildBy(By.xpath("div[8]"))
      // the statistics columns are organized in an unordered list embedded in a layout div
      .findChildBy(By.cssSelector("div.row ul"))
      // extract each statistics columns
      .findChildrenBy(By.cssSelector("li > div.card"))
      .stream()
      // build a Rating Info view object for each of these divs
      .map(RitAreaOfStudyLink::new)
      .toList();
  }

  public List<RatingInfoView> getRatingViews() {
    // the Ratings panel is in the fifth <div> of the main content
    // WARN: this is fragile code
    return mainContentPanel.findChildBy(By.xpath("div[5]"))
      // the statistics columns are organized in a layout div
      .findChildBy(By.cssSelector("div.row"))
      // extract each statistics columns
      .findChildrenBy(By.cssSelector("div.card"))
      .stream()
      // build a Rating Info view object for each of these divs
      .map(RatingInfoView::new)
      .toList();
  }
}
