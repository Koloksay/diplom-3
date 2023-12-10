package src.main.java.site.nomoreparties.stellarburgers.po;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotPasswordPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By enterButton = By.cssSelector("a.Auth_link__1fOlj");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
    }

    public By getEnterButton() {
        return enterButton;
    }

    @Step("Кликнуть по кнопке 'Войти'")
    public void clickForEnterButton(){
        scrollToEnterButton();
        driver.findElement(enterButton).click();
    }
    @Step("Скролл до кнопки 'Войти'")
    public void scrollToEnterButton() {
        WebElement element = driver.findElement(enterButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
