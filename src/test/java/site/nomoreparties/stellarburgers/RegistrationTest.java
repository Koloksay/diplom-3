package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import io.restassured.response.ValidatableResponse;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import site.nomoreparties.stellarburgers.data.ClientData;
import site.nomoreparties.stellarburgers.data.ClientGenerator;
import site.nomoreparties.stellarburgers.restClient.ClientClient;
import site.nomoreparties.stellarburgers.restClient.LoginClient;
import src.main.java.site.nomoreparties.stellarburgers.po.HeadPage;
import src.main.java.site.nomoreparties.stellarburgers.po.LoginPage;
import src.main.java.site.nomoreparties.stellarburgers.po.RegisterPage;

import static org.junit.Assert.assertTrue;


public class RegistrationTest {
    private WebDriver driver;
    private ClientClient clientClient;
    private String bearerToken;
    private WebDriverWait wait;
    private String email;
    private String name;
    private String password;
    @Before
    public void setUp() {
        driver = Browsers.getDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("https://stellarburgers.nomoreparties.site/");

        ClientGenerator clientGenerator = new ClientGenerator();
        email = clientGenerator.generateRandomEmail().toLowerCase();
        name = RandomStringUtils.randomAlphabetic(10);
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
    @DisplayName("Можно зарегистрировать нового клиента")
    public void registrationNewClientTest() {
        //создали объекты страниц
        HeadPage headPage = new HeadPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        // кликнули по кнопке "Войти в аккаунт"
        headPage.clickToEnterInAccountButton();

        //кликнули по ссылке "Зарегистрироваться"
        loginPage.scrollToRegistrationButton();
        loginPage.clickForRegistrationButton();

        //дождались загрузки страницы "Регистрация"
        By confirmRegistrationButton= registerPage.getRegistrationButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmRegistrationButton));
        password = RandomStringUtils.randomAlphabetic(10);
        registerPage.fieldForm(name,email,password);
        registerPage.clickForRegistrationButton();

        // Проверяем, что текст соответствует ожидаемому
        assertTrue("Ожидается появления кнопки с текстом 'Вход'", loginPage.isEnterButtonDisplayed());
    }

    @Test
    @DisplayName("Поле Пароль не принимает значение меньше 6 символов")
    public void registrationFailedIfNewClientWith5CharInPasswordTest() {
        //создали объекты страниц
        HeadPage headPage = new HeadPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        // кликнули по кнопке "Войти в аккаунт"
        headPage.clickToEnterInAccountButton();

        //кликнули по ссылке "Зарегистрироваться"
        loginPage.scrollToRegistrationButton();
        loginPage.clickForRegistrationButton();

        //дождались загрузки страницы "Регистрация"
        By confirmRegistrationButton= registerPage.getRegistrationButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmRegistrationButton));
        password = RandomStringUtils.randomAlphabetic(5);
        registerPage.fieldForm(name,email,password);
        registerPage.clickForRegistrationButton();

        // Проверяем, что появилось сообщение об ошибке
        assertTrue("Ожидается появления сообщения об ошибке", registerPage.isErrorMessageDisplayed());
    }
}