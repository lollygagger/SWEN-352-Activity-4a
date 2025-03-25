package edu.rit.swen253.utils;

import edu.rit.swen253.page.AbstractPage;
import edu.rit.swen253.page.PageFactory;
import edu.rit.swen253.test.AbstractWebTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.logging.Logger;

/**
 * A Utility class for Selenium.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public final class SeleniumUtils {
  private static final Logger logger = Logger.getLogger(SeleniumUtils.class.getName());

  /**
   * Set up the {@link WebDriver} into the Utilities class.  This <strong>must</strong> be called
   * by the {@link AbstractWebTest#setUpSeleniumDriver()} method to set up the standard
   * set of {@link FluentWait} objects.
   */
  public static void setupDriver() {
    final OperatingSystem operatingSystem = OperatingSystem.discover();
    final BrowserType browserType = BrowserType.discover();
    logger.info("Operating system: " + operatingSystem);
    logger.info("Browser: " + browserType);
    logger.info("Screen Mode: " + ScreenMode.discover());
    browserType.configureDriverExecutable(operatingSystem);
    theDriver = buildDriver();
    shortWait = makeWait(SHORT_DELAY);
    mediumWait = makeWait(MEDIUM_DELAY);
    longWait = makeWait(LONG_DELAY);
  }

  public static void shutdownDriver() {
    if (theDriver != null) {
      logger.info("Quiting the driver.");
      theDriver.quit();
    } else {
      logger.warning("No driver to quit.");
    }
  }

  public static WebDriver getDriver() {
    return theDriver;
  }

  public static <P extends AbstractPage> P navigateToPage(String url, PageFactory<P> pageFactory) {
    theDriver.navigate().to(url);
    return pageFactory.createPage();
  }

  public static String getCurrentUrl() {
    return theDriver.getCurrentUrl();
  }

  public static void refresh() {
    theDriver.navigate().refresh();
  }

  public static FluentWait<WebDriver> getShortWait() {
    return SeleniumUtils.shortWait;
  }

  public static FluentWait<WebDriver> getMediumWait() {
    return SeleniumUtils.mediumWait;
  }

  public static FluentWait<WebDriver> getLongWait() {
    return SeleniumUtils.longWait;
  }

  /**
   * Make a {@linkplain FluentWait Selenium wait operator} with a specific timeout.
   *
   * @param timeout the length of time to wait for the condition
   * @return a {@link FluentWait} operator that can be used for any condition
   */
  public static FluentWait<WebDriver> makeWait(final Duration timeout) {
    return new FluentWait<>(theDriver)
      .withTimeout(timeout)
      .pollingEvery(Duration.ofMillis(SHORT_INTERVAL))
      .ignoring(NoSuchElementException.class);
  }

  /**
   * Make a new {@link Actions} object from the test's Selenium driver.
   */
  public static Actions makeAction() {
    return new Actions(theDriver);
  }

  /**
   * Execute some JavaScript code in the browser against a specific DOM element.
   *
   * @param script  the JavaScript code, use "arguments[0]" to reference the element
   * @param element  the DOM element being referenced in the code
   */
  public static void executeScript(final String script, final WebElement element) {
    ((JavascriptExecutor)getDriver()).executeScript(script, element);
  }

  //
  // Private
  //

  /**
   * A short interval is half a second long (500ms).
   */
  private static final int SHORT_INTERVAL = 500;

  /**
   * A short wait delay is one second long.
   */
  private static final Duration SHORT_DELAY = Duration.ofSeconds(1);

  /**
   * A delay wait is five second long.
   */
  private static final Duration MEDIUM_DELAY = Duration.ofSeconds(5);

  /**
   * A long wait delay is ten second long.
   */
  private static final Duration LONG_DELAY = Duration.ofSeconds(10);

  private static FluentWait<WebDriver> shortWait;
  private static FluentWait<WebDriver> mediumWait;
  private static FluentWait<WebDriver> longWait;

  /**
   * The {@link WebDriver} created for a single test run.
   */
  private static WebDriver theDriver;

  /**
   * Initialize the driver depending upon the type of browser and system properties.
   *
   * @return WebDriver (ChromeDriver or FirefoxDriver)
   */
  private static WebDriver buildDriver() {
    // create essential browser capabilities
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
    // build the driver for a specific Browser
    final WebDriver driver = BrowserType.discover().buildDriver(capabilities);
    // more driver configuration
    driver.manage().window().setSize(ScreenMode.discover().getScreenSize());
    driver.manage().timeouts().implicitlyWait(ONE_SECOND);
    driver.manage().timeouts().pageLoadTimeout(TEN_SECONDS);
    //
    return driver;
  }

  private static final Duration ONE_SECOND = Duration.ofSeconds(1);
  private static final Duration TEN_SECONDS = Duration.ofSeconds(10);


  /* hide ctor to complete the Utility idiom */
  private SeleniumUtils() {
  }
}
