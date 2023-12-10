package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import site.nomoreparties.stellarburgers.data.ClientData;
import site.nomoreparties.stellarburgers.data.ClientGenerator;
import site.nomoreparties.stellarburgers.restClient.ClientClient;
import site.nomoreparties.stellarburgers.restClient.LoginClient;
import site.nomoreparties.stellarburgers.restClient.RegisterClient;
import src.main.java.site.nomoreparties.stellarburgers.po.HeadPage;


import static org.junit.Assert.assertTrue;

public class DesignerTest {

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
        driver.get("https://stellarburgers.nomoreparties.site/");

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

    @Test
    @DisplayName("Можно перейти в раздел 'Начинки'")
    public void canSelectCharFilling(){
        HeadPage headPage = new HeadPage(driver);
        headPage.clickToFillingsChapter();
        assertTrue("Не виден ожидаемый текст'Начинки'",headPage.isFillingsTextDisplayed());
    }

    @Test
    @DisplayName("Можно перейти в раздел 'Соусы'")
    public void canSelectCharSauces(){
        HeadPage headPage = new HeadPage(driver);
        headPage.clickToSaucesChapter();
        assertTrue("Не виден ожидаемый текст'Соусы'",headPage.isSaucesTextDisplayed());
    }

    @Test
    @DisplayName("Можно перейти в раздел 'Булки'")
    public void canSelectCharBut(){
        HeadPage headPage = new HeadPage(driver);
        headPage.clickToSaucesChapter();
        headPage.clickToButChapter();
        assertTrue("Не виден ожидаемый текст'Булки'",headPage.isSaucesTextDisplayed());
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
}