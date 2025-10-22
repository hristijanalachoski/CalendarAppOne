package util;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import io.qameta.allure.Step;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.testng.annotations.*;

import pages.*;

@Listeners({ITestListenerUtility.class})
public class DriverSetup extends ConfigReader {

    protected static IOSDriver driver;
    private final AppiumServerManager appiumServerManager = new AppiumServerManager();

    protected CalendarHomePage calendarHomePage;
    protected NewEventPage newEventPage;

    @BeforeSuite
    public void startAppiumServer() {
        appiumServerManager.startAppiumServerWithCustomFlags();
    }

    @Step("Starting XCUITest driver")
    @BeforeMethod
    public void setUp() {

        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName(getProperty("device.name"))
                .setPlatformVersion(getProperty("platform.version"))
                .setBundleId(getProperty("bundle.id"))
                .setNoReset(false)
                .setFullReset(false)
                .setAutoAcceptAlerts(true);

        try {
            driver = new IOSDriver(new URI(GlobalVariables.appiumLocalUrl).toURL(), options);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        calendarHomePage = new CalendarHomePage(driver);
        newEventPage = new NewEventPage(driver);
    }

    @Step("Stopping XCUITest driver")
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }

    @AfterSuite(alwaysRun = true)
    public void stopAppiumServer() {
        appiumServerManager.stopAppiumServer();
    }
}
