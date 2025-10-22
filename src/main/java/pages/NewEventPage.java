package pages;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;

import util.GlobalVariables;

public class NewEventPage {

    protected IOSDriver driver;

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTextField[`name == \"Title\"`]")
    private RemoteWebElement titleTextField;

    @iOSXCUITFindBy(accessibility = "add-button")
    private RemoteWebElement addButton;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeCell[contains(@name, 'Starts')]/XCUIElementTypeButton/XCUIElementTypeButton)[2]")
    private RemoteWebElement startsHourPickerButton;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypePickerWheel)[1]")
    private RemoteWebElement hourPicker;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypePickerWheel)[2]")
    private RemoteWebElement minutePicker;

    public NewEventPage(IOSDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Step("New event page is loaded")
    public boolean newEventPageLoaded() {
        return new WebDriverWait(driver, GlobalVariables.shortTimeout).until(ExpectedConditions.visibilityOf(titleTextField)).isDisplayed();
    }

    @Step("Add button is enabled/disabled")
    public boolean isAddButtonEnabled() {
        return addButton.isEnabled();
    }

    @Step("Entering title: {title}")
    public void enterTitle(String title) {
        titleTextField.clear();
        titleTextField.sendKeys(title);
    }

    @Step("Choosing starting hour: {hour}, minute: {minutes}" )
    public void chooseStartHour(String hour, String minutes) {
        startsHourPickerButton.click();
        new WebDriverWait(driver, GlobalVariables.shortTimeout).until(ExpectedConditions.visibilityOf(hourPicker)).sendKeys(hour);
        minutePicker.sendKeys(minutes);
    }
}
