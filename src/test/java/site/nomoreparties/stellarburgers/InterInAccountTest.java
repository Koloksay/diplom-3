package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import site.nomoreparties.stellarburgers.data.ClientData;
import site.nomoreparties.stellarburgers.data.ClientGenerator;
import site.nomoreparties.stellarburgers.po.PersonalPage;
import site.nomoreparties.stellarburgers.restClient.ClientClient;
import site.nomoreparties.stellarburgers.restClient.LoginClient;
import site.nomoreparties.stellarburgers.restClient.RegisterClient;
import src.main.java.site.nomoreparties.stellarburgers.po.HeadPage;
import src.main.java.site.nomoreparties.stellarburgers.po.LoginPage;
import src.main.java.site.nomoreparties.stellarburgers.po.RegisterPage;
import src.main.java.site.nomoreparties.stellarburgers.po.ForgotPasswordPage;

import static org.junit.Assert.assertEquals;

public class InterInAccountTest {

    private WebDriver driver;
    private ClientClient clientClient;
    private String bearerToken;
    private WebDriverWait wait;
    private String email;
    private String name;
    private String password;

    @Before
    public void setup() {
        driver = Browsers.getDriver();
        wait = new WebDriverWait(driver, 10);

        //создаём клиента
        RegisterClient registerClient = new RegisterClient();
        clientClient = new ClientClient();
        ClientData client = ClientGenerator.getRandomClient();
        ValidatableResponse response = registerClient.createNewClient(client);

        // записываем данные для дальнейшего использования
        bearerToken = response.extract().path("accessToken");
        email = client.getEmail().toLowerCase();
        name = client.getName();
        password= client.getPassword();
    }

    @After
    @Description("Удаляем тестовые данные")
    public void tearDown() {
        //закрываем браузер
        driver.quit();
        //удаляем тестового клиента, если он был ранее создан
        clientClient = new ClientClient();
        LoginClient loginClient = new LoginClient();
        ClientData client = new ClientData(email,password,name);
        ValidatableResponse response = loginClient.loginClient(client);
        bearerToken = response.extract().path("accessToken");

        if (bearerToken != null) {
            clientClient.deleteClient(bearerToken);
        }
    }

    @Test
    @DisplayName("Можно войти через клик по кнопке 'Войти в аккаунт' на главной странице")
    public void enterFromClickEnterInHeadPageTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        //создали объекты страниц
        HeadPage headPage = new HeadPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        // кликнули по кнопке "Войти в аккаунт"
        headPage.clickToEnterInAccountButton();
        //заполнили форму
        loginPage.fieldForm(email,password);
        //клик по "Войти"
        loginPage.clickForEnterButton();
        // Проверяем, что перешли на правильную страницу
        String expectedUrl = "https://stellarburgers.nomoreparties.site/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = driver.getCurrentUrl();
        assertEquals("URL не совпадает с ожидаемым", expectedUrl, actualUrl);
    }
    @Test
    @DisplayName("Можно войти через клик по кнопке 'Личный кабинет' на главной странице")
    public void enterFromClickToEnterInAccountButtonTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        //создали объекты страниц
        HeadPage headPage = new HeadPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalPage personalPage = new PersonalPage(driver);
       //кликнули по кнопке "Личный кабинет"
        headPage.clickToEnterInAccountButton();
        //заполнили форму
        loginPage.fieldForm(email,password);
        //клик по "Войти"
        loginPage.clickForEnterButton();
        //ждём входа
        String expectedUrl = "https://stellarburgers.nomoreparties.site/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        //клик по "Личный кабинет"
        headPage.clickToEnterInPersonalButton();
        String expectedUrlPersonal = "https://stellarburgers.nomoreparties.site/account/profile";
        wait.until(ExpectedConditions.urlToBe(expectedUrlPersonal));
        By nameField=personalPage.getNameField();
        String actualName = driver.findElement(nameField).getAttribute("value");
        // сравниваем с ожидаемым значением name
        assertEquals(name, actualName);
    }
    @Test
    @DisplayName("Можно войти через клик по кнопке 'Личный кабинет' в форме регистрации")
    public void enterFromClickToEnterInRegistrationPage() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        //создали объекты страниц
        RegisterPage registerPage = new RegisterPage(driver);
        HeadPage headPage = new HeadPage(driver);
        PersonalPage personalPage = new PersonalPage(driver);
        By selector = registerPage.getEnterButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        LoginPage loginPage = new LoginPage(driver);
        //кликнули по кнопке "Войти"
        registerPage.clickForEnterButton();
        //заполнили форму
        loginPage.fieldForm(email,password);
        //клик по "Войти"
        loginPage.clickForEnterButton();
        //ждём входа
        String expectedUrl = "https://stellarburgers.nomoreparties.site/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        //клик по "Личный кабинет"
        headPage.clickToEnterInPersonalButton();
        String expectedUrlPersonal = "https://stellarburgers.nomoreparties.site/account/profile";
        wait.until(ExpectedConditions.urlToBe(expectedUrlPersonal));
        By nameField=personalPage.getNameField();
        String actualName = driver.findElement(nameField).getAttribute("value");
        // сравниваем с ожидаемым значением name
        assertEquals(name, actualName);
    }
    @Test
    @DisplayName("Можно войти через клик по кнопке 'Войти' в форме восстановления пароля")
    public void enterFromClickToEnterInForgotPasswordPage() {
        driver.get("https://stellarburgers.nomoreparties.site/forgot-password");
        //создали объекты страниц
        HeadPage headPage = new HeadPage(driver);
        PersonalPage personalPage = new PersonalPage(driver);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        By selector = forgotPasswordPage.getEnterButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        //кликнули по кнопке "Войти"
        forgotPasswordPage.clickForEnterButton();
        //заполнили форму
        loginPage.fieldForm(email,password);
        //клик по "Войти"
        loginPage.clickForEnterButton();
        //ждём входа
        String expectedUrl = "https://stellarburgers.nomoreparties.site/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        //клик по "Личный кабинет"
        headPage.clickToEnterInPersonalButton();
        String expectedUrlPersonal = "https://stellarburgers.nomoreparties.site/account/profile";
        wait.until(ExpectedConditions.urlToBe(expectedUrlPersonal));
        By nameField=personalPage.getNameField();
        String actualName = driver.findElement(nameField).getAttribute("value");
        // сравниваем с ожидаемым значением name
        assertEquals(name, actualName);
    }
}