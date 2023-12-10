package site.nomoreparties.stellarburgers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browsers {

    private static final String CHROME_DRIVER_PATH= "C:\\Users\\Admin\\chromedriver-win64\\chromedriver.exe";
    private static final String YANDEX_DRIVER_PATH= "C:\\Users\\Admin\\chromedriver-win64\\yandexdriver.exe";


    public static WebDriver getDriver(){
        String browserProperty=System.getProperty("test.browser","yandex");
        BrowserType browserType = BrowserType.valueOf(browserProperty.toUpperCase());
        switch (browserType){
            case CHROME:
                System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
                return new ChromeDriver();

            case YANDEX:
                System.setProperty("webdriver.chrome.driver", YANDEX_DRIVER_PATH);
                return new ChromeDriver();
            default:
                throw new RuntimeException("Browser undefined");
        }
    }
}
