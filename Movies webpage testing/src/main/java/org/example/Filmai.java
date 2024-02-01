package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Filmai {
    public static WebDriver browser;
    public static final String MOVIE_NAME = "Kristinos filmas";
    public static final String MOVIE_NAME2 = "Kristinos filmas 2";
    public static final String MOVIE_GENRE = "Veiksmo";
    public static final String MOVIE_ACTOR = "Kristina";
    public static final String MOVIE_DIRECTOR = "Mikalauskiene";
    public static final String DURATION = "555";
    public static void setup() {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--ignore-certificate-errors");

        browser = new ChromeDriver(chromeOptions);
        browser.get("https://programavimoabc.lt/filmai.php");

    }
    public static void main(String[] args) {
        setup();


        fillMovieForm(MOVIE_NAME);
        clickOnElement(By.name("insert"));
        System.out.println(getNotificationMessage());

        String id = findMovieId();

        fillIdField(id);
        fillMovieForm(MOVIE_NAME2);
        clickOnElement(By.name("update"));
        System.out.println(getNotificationMessage());

        fillIdField(id);
        clickOnElement(By.name("delete"));
        System.out.println(getNotificationMessage());

        closeBrowser();
    }

    public static void clickOnElement(By locator){browser.findElement(locator).click();}

    public static void fillMovieForm(String name) {

        WebElement nameInput = browser.findElement(By.name("pavadinimas"));
        nameInput.sendKeys(name);

        WebElement genreInput = browser.findElement(By.name("zanras"));
        genreInput.sendKeys(MOVIE_GENRE);

        WebElement actorInput = browser.findElement(By.name("aktoriai"));
        actorInput.sendKeys(MOVIE_ACTOR);

        WebElement directorInput = browser.findElement(By.name("rezisierius"));
        directorInput.sendKeys(MOVIE_DIRECTOR);

        WebElement durationInput = browser.findElement(By.name("trukme"));
        durationInput.sendKeys(DURATION);

    }
    public static String getNotificationMessage(){
        return browser.findElement(By.xpath("/html/body/div[2]")).getText();
    }

    public static String findMovieId(){
        WebElement nameInput = browser.findElement(By.name("pavadinimas"));
        nameInput.sendKeys(MOVIE_NAME);

        clickOnElement(By.name("search"));

        return browser.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[3]")).getText();
    }

    public static void fillIdField(String id){
        WebElement idInput = browser.findElement(By.name("id"));
        idInput.sendKeys(id);
    }

    public static void closeBrowser(){
        browser.quit();
    }
}
