package src.main.java.site.nomoreparties.stellarburgers.po;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeadPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By enterInAccountButton = By.cssSelector("button.button_button__33qZ0");
    private By enterInPersonalButton = By.cssSelector("#root > div > header > nav > a > p");
    private final By designerText = By.xpath("//h1[text()='Соберите бургер']");
    private By butChapter = By.cssSelector("div.tab_tab__1SPyG:nth-child(1)");
    private By saucesChapter = By.cssSelector("div.tab_tab__1SPyG:nth-child(2)");
    private By fillingsChapter = By.cssSelector("div.tab_tab__1SPyG:nth-child(3)");
    private By activeChapter=By.cssSelector("div.tab_tab__1SPyG.tab_tab_type_current__2BEPc > span");

    private final By butText = By.xpath("//h2[text()='Булки']");
    private final By saucesText = By.xpath("//h2[text()='Соусы']");
    private final By fillingsText = By.xpath("//h2[text()='Начинки']");


    public HeadPage(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
    }

    public By getEnterInAccountButton() {
        return enterInAccountButton;
    }

    public By getDesignText() {
        return designerText;
    }

    @Step("Убедиться, что на странице есть кнопка 'Войти в личный кабинет'")
    public boolean isEnterInPersonalButtonDisplayed() {
        WebElement header = driver.findElement(enterInPersonalButton);
        return header.isDisplayed();
    }

    @Step("Кликнуть по кнопке 'Войти'")
    public void clickToEnterInAccountButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(enterInAccountButton));
        scrollToAndClick(enterInAccountButton);
    }

    @Step("Кликнуть по кнопке 'Войти в личный кабинет'")
    public void clickToEnterInPersonalButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(enterInPersonalButton));
        scrollToAndClick(enterInPersonalButton);
    }

    @Step("Убедиться, что на странице есть текст 'Собери бургер'")
    public boolean isDesignerTextDisplayed() {
        WebElement header = driver.findElement(designerText);
        return header.isDisplayed();
    }
    @Step("Кликнуть по разделу 'Булки'")
    public void clickToButChapter() {
        scrollToAndClick(butChapter);
    }
    @Step("Кликнуть по разделу 'Соусы'")
    public void clickToSaucesChapter() {
        scrollToAndClick(saucesChapter);
    }
    @Step("Кликнуть по разделу 'Начинки'")
    public void clickToFillingsChapter() {
        scrollToAndClick(fillingsChapter);
    }
    @Step("Убедиться, что виден заголовок раздела 'Булки'")
    public boolean isButTextDisplayed() {
        WebElement header = driver.findElement(butText);
        return header.isDisplayed();
    }
    @Step("Убедиться, что виден заголовок раздела 'Соусы'")
    public boolean isSaucesTextDisplayed() {
        WebElement header = driver.findElement(saucesText);
        return header.isDisplayed();
    }
    @Step("Убедиться, что виден заголовок раздела 'Начинки'")
    public boolean isFillingsTextDisplayed() {
        WebElement header = driver.findElement(fillingsText);
        return header.isDisplayed();
    }

    @Step("Проверить какой текст в выбранном разделе")
    public String getActiveChapter(){
        WebElement active = driver.findElement(activeChapter);
        return active.getText();
    }

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