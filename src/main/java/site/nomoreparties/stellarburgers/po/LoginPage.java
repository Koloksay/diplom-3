package src.main.java.site.nomoreparties.stellarburgers.po;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private By enterInAccountButton = By.cssSelector("button.button_button__33qZ0");
    private By registrationButton = By.cssSelector("a[href='/register'].Auth_link__1fOlj");
    private By emailInput = By.cssSelector("input.text[name='name']");
    private By passwordInput = By.cssSelector("input.text[type='password']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait=new WebDriverWait(driver, 10);
    }

    public By getEmailInput() {
        return emailInput;
    }
    public By getPasswordInput() {
        return passwordInput;
    }
    public By getEnterInAccountButton() {
        return enterInAccountButton;
    }
    public By getRegistrationButton() {
        return registrationButton;
    }

    @Step("Убедиться, что на странице есть кнопка 'Зарегистрироваться'")
    public boolean isRegistrationButtonDisplayed() {
        WebElement header = driver.findElement(registrationButton);
        return header.isDisplayed();
    }
    @Step("Убедиться, что на странице есть кнопка 'Войти'")
    public boolean isEnterButtonDisplayed() {
        WebElement header = driver.findElement(enterInAccountButton);
        return header.isDisplayed();
    }
    @Step("Кликнуть по кнопке 'Зарегистрироваться'")
    public void clickForRegistrationButton(){
        driver.findElement(registrationButton).click();
    }
    @Step("Скролл до кнопки 'Зарегистрироваться'")
    public void scrollToRegistrationButton() {
        WebElement element = driver.findElement(registrationButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    @Step("Кликнуть по кнопке 'Войти'")
    public void clickForEnterButton(){
        driver.findElement(enterInAccountButton).click();
    }
    @Step("Заполнить поле 'Email'")
    public void inputEmailField(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }
    @Step("Заполнить поле 'password'")
    public void inputPasswordField(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }
    @Step("Заполнить форму регистрации")
    public void fieldForm(String email,String password) {
        inputEmailField(email);
        inputPasswordField(password);
    }
}