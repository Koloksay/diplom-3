package src.main.java.site.nomoreparties.stellarburgers.po;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private final By registrationButton = By.cssSelector("button.button_button__33qZ0");
    private final By enterButton=By.cssSelector("a.Auth_link__1fOlj");
    private final By nameInput = By.xpath("//*[@id='root']//label[text()='Имя']/following-sibling::input[@name='name']");
    private final By emailInput = By.xpath("//*[@id='root']//label[text()='Email']/following-sibling::input[@name='name']");
    private final By passwordInput = By.cssSelector("input.text[type='password']");
    private final By incorrectPasswordMessage = By.cssSelector("div.input_type_password.input_status_error");


    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public By getRegistrationButton() {
        return registrationButton;
    }

    public By getEmailInput() {
        return emailInput;
    }

    public By getNameInput() {
        return nameInput;
    }

    public By getPasswordInput() {
        return passwordInput;
    }

    public By getEnterButton() {
        return enterButton;
    }

    @Step("Кликнуть по кнопке 'Зарегистрироваться'")
    public void clickForRegistrationButton() {
        driver.findElement(registrationButton).click();
    }

    @Step("Кликнуть по кнопке 'Войти'")
    public void clickForEnterButton() {
        driver.findElement(enterButton).click();
    }
    @Step("Убедиться, что на странице есть кнопка 'Войти'")
    public boolean isEnterButtonDisplayed() {
        WebElement header = driver.findElement(enterButton);
        return header.isDisplayed();
    }
    @Step("Убедиться, что на странице есть кнопка 'Зарегистрироваться'")
    public boolean isRegistrationButtonDisplayed() {
        WebElement header = driver.findElement(registrationButton);
        return header.isDisplayed();
    }
    @Step("Убедиться, что на странице появилось сообщение об ошибке")
    public boolean isErrorMessageDisplayed() {
        WebElement header = driver.findElement(incorrectPasswordMessage);
        return header.isDisplayed();
    }
    @Step("Заполнить поле 'Name'")
    public void inputNameField(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }
    @Step("Заполнить поле 'Email'")
    public void inputEmailField(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }
    @Step("Заполнить поле 'password'")
    public void inputPasswordField(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }
    @Step("Заполнить форуму регистрации")
    public void fieldForm(String name, String email,String password) {
        inputNameField(name);
        inputEmailField(email);
        inputPasswordField(password);
    }
}