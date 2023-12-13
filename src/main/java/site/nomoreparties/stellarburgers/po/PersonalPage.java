package site.nomoreparties.stellarburgers.po;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PersonalPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private By enterInDesigner = By.cssSelector("#root > div > header > nav > ul > li:nth-child(1) > a > p");
    private By logoButton = By.cssSelector(".AppHeader_header__logo__2D0X2>:first-child");
    private By nameField = By.cssSelector("input[name='Name']");
    private By exitButton = By.cssSelector("button.Account_button__14Yp3");

    public PersonalPage(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
    }

    public By getEnterInDesigner() {
        return enterInDesigner;
    }

    public By getLogoButton() {
        return logoButton;
    }

    public By getExitButton() {
        return exitButton;
    }

    public By getNameField() {
        return nameField;
    }

    @Step("Кликнуть по кнопке 'Зарегистрироваться'")
    public void clickForEnterInDesignerButton() {
        driver.findElement(enterInDesigner).click();
    }
    @Step("Кликнуть по логотипу Stellar Burger")
    public void clickForLogoButton() {
        scrollToAndClick(logoButton);
    }
    @Step("Кликнуть по кнопке 'Выйти'")
    public void clickForExitButton() {
        driver.findElement(exitButton).click();
    }
    @Step("Дождаться полной загрузки страницы")
    public void waitExitButton(){
    wait.until(ExpectedConditions.elementToBeClickable(exitButton));}

    private void scrollToAndClick(By elementLocator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Ждем пока элемент станет видимым
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));

        // Ждем пока элемент станет кликабельным
        wait.until(ExpectedConditions.elementToBeClickable(element));

        // Клик
        element.click();
    }
}