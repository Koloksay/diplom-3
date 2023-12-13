package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EnterInPersonalTest {

    private WebDriver driver;
    private ClientClient clientClient;
    private RegisterClient registerClient;
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
        registerClient = new RegisterClient();
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
    @DisplayName("Авторизованный пользователь может войти в личный кабинет через кнопку 'Личный кабинет' на главной странице")
    public void enterInPersonalFromClickEnterInPersonalButtonInHeadPageTest() {
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
        //клик по кнопке "Личный кабинет"
        headPage.clickToEnterInPersonalButton();

        // Проверяем, что перешли на правильную страницу
        String expectedUrl = "https://stellarburgers.nomoreparties.site/account/profile";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = driver.getCurrentUrl();
        assertEquals("URL не совпадает с ожидаемым", expectedUrl, actualUrl);
    }

    @Test
    @DisplayName("Авторизованный пользователь может перейти из личного кабинета в конструктор (главную страницу) по клику на 'Конструктор'")
    public void enterFromClickEnterInHeadTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        //создали объекты страниц
        HeadPage headPage = new HeadPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalPage personalPage=new PersonalPage(driver);
        // кликнули по кнопке "Войти в аккаунт"
        headPage.clickToEnterInAccountButton();
        //заполнили форму
        loginPage.fieldForm(email,password);
        //клик по "Войти"
        loginPage.clickForEnterButton();
        //клик по кнопке "Личный кабинет"
        headPage.clickToEnterInPersonalButton();
        //клик по кнопке "Конструктор"
        personalPage.clickForEnterInDesignerButton();
        // Перешли на правильную страницу
        String expectedUrl = "https://stellarburgers.nomoreparties.site/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        //проверяем, что виден текст "Соберите бургер"
        assertTrue("Не виден ожидаемый текст'Соберите бургер'", headPage.isDesignerTextDisplayed());
    }

    @Test
    @DisplayName("Авторизованный пользователь может перейти из личного кабинета в конструктор (главную страницу) по клику на логотип")
    public void enterFromClickLogoInHeadPageTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        //создали объекты страниц
        HeadPage headPage = new HeadPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalPage personalPage=new PersonalPage(driver);
        // кликнули по кнопке "Войти в аккаунт"
        headPage.clickToEnterInAccountButton();
        //заполнили форму
        loginPage.fieldForm(email,password);
        //клик по "Войти"
        loginPage.clickForEnterButton();
        //клик по кнопке "Личный кабинет"
        headPage.clickToEnterInPersonalButton();
        //клик по логотипу
        personalPage.clickForLogoButton();
        // Перешли на главную страницу
        String expectedUrl = "https://stellarburgers.nomoreparties.site/";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        //проверяем, что виден текст "Соберите бургер"
        assertTrue("Не виден ожидаемый текст'Соберите бургер'", headPage.isDesignerTextDisplayed());
    }

    @Test
    @DisplayName("Авторизованный пользователь может выйти из аккаунта по кнопке 'Выйти' в личном кабинете")
    public void exitFromClickExitInPersonalTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        //создали объекты страниц
        HeadPage headPage = new HeadPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalPage personalPage=new PersonalPage(driver);
        // кликнули по кнопке "Войти в аккаунт"
        headPage.clickToEnterInAccountButton();
        //заполнили форму
        loginPage.fieldForm(email,password);
        //клик по "Войти"
        loginPage.clickForEnterButton();
        //клик по кнопке "Личный кабинет"
        headPage.clickToEnterInPersonalButton();
        //дождаться загрузки страницы
        personalPage.waitExitButton();
        //кликнуть по кнопке "Выход"
        personalPage.clickForExitButton();
        String expectedUrl = "https://stellarburgers.nomoreparties.site/login";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        //проверяем, что виден текст "Соберите бургер"
        assertTrue("Не видна кнопка 'Войти'", loginPage.isEnterButtonDisplayed());
    }
}