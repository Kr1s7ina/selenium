package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;


public class Baldai1 {

    public static WebDriver browser;
    public static final String FIRST_NAME = "Vardenis";
    public static final String LAST_NAME = "Pavardenis";
    public static final String PHONE_NUMBER = "860000000";
    public  static final String PASSWORD = "NamasNamukas123@";
    public static final String SEARCH_KEYWORD = "Lova";
    public static final int WAIT_DURATION_SECONDS = 2;



    public static void setup() {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--ignore-certificate-errors");

        browser = new ChromeDriver(chromeOptions);
        browser.get("https://www.baldai1.lt/");

    }
    
    public static void main(String[] args) {
        setup();
        closeCookies();

        clickUserIcon();
        clickRegisterButton();

        String email = generateUniqueEmail();
        fillRegistrationForm(email, true);
        clickAgreementCheckBox();
        submitRegisterForm();

        System.out.println(getNotification());

        clickUserIcon();
        clickLogoutButton();
        clickUserIcon();
        checkIfLoginButtonIsVisible();
        clickLoginButton();

//        //blogas prisujungimas
//        fillLoginForm(generateUniqueEmail());
        //Prisijungimas
        fillLoginForm(email);

        clickLoginFormButton();
        System.out.println(getNotification());

        clickOnElement(By.id("search_input"));
        searchInput(SEARCH_KEYWORD);
        clickOnElement(By.className("ty-search-magnifier"));
        int resultCount = findSearchResultCount();
        String searchResult =  compareResult(resultCount);
        System.out.println(searchResult);
    }

    public static void checkIfLoginButtonIsVisible() {
        boolean isLoginVisible =isElementVisible(By.xpath("//*[@id=\"account_info_166\"]/div/a[1]"));
        if (isLoginVisible){
            System.out.println("atsijungimas sekmingas");
        }
        else{
            System.out.println("atsijungimas nesekmingas");
        }
    }

    public static void clickAgreementCheckBox() {
        clickOnElement(By.id("gdpr_agreements_user_registration"));
    }

    public static void clickLoginFormButton() {
        clickOnElement(By.xpath("//*[@id=\"tygh_main_container\"]/main/div/section[2]/div/div/div[1]/div/div/div/form/div[3]/div[2]/button"));
    }

    public static void clickLoginButton() {
        clickOnElement(By.xpath("//*[@id=\"account_info_166\"]/div/a[1]"));
    }

    public static void clickLogoutButton() {
        clickOnElement(By.xpath("//*[@id=\"account_info_166\"]/div/a"));
    }

    public static void submitRegisterForm() {
        clickOnElement(By.name("dispatch[profiles.update]"));
    }

    public static void clickRegisterButton() {
        clickOnElement(By.xpath("//*[@id=\"account_info_166\"]/div/a[2]"));
    }

    public static void clickUserIcon() {
        clickOnElement(By.xpath("//*[@id=\"sw_dropdown_drop_id\"]/a"));
    }


    public static void clickOnElement(By locator){browser.findElement(locator).click();}

    public static void fillRegistrationForm(String email, boolean fillFirstName) {

        if (fillFirstName){
            WebElement firstNameInput = browser.findElement(By.id("elm_6"));
            firstNameInput.sendKeys(FIRST_NAME);
        }

        WebElement lastNameInput = browser.findElement(By.id("elm_7"));
        lastNameInput.sendKeys(LAST_NAME);

        WebElement phoneNumberInput = browser.findElement(By.id("elm_9"));
        phoneNumberInput.sendKeys(PHONE_NUMBER);

        WebElement emailInput = browser.findElement(By.id("email"));
        emailInput.sendKeys(email);

        WebElement passwordInput = browser.findElement(By.id("password1"));
        passwordInput.sendKeys(PASSWORD);

        WebElement confirmPasswordInput = browser.findElement(By.id("password2"));
        confirmPasswordInput.sendKeys(PASSWORD);
    }

    public static String generateUniqueEmail() {
        return "test" + UUID.randomUUID().toString() + "@yopmail.com";
    }

    public static void closeCookies() {
        WebElement agree = browser.findElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) browser;
        javascriptExecutor.executeScript("arguments[0].click()", agree);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fillLoginForm(String email){
        WebElement emailInput = browser.findElement(By.id("login_main_login"));
        emailInput.sendKeys(email);

        WebElement passwordInput = browser.findElement(By.id("psw_main_login"));
        passwordInput.sendKeys(PASSWORD);
    }

    public static void searchInput(String searchKeyword){
        WebElement searchInput = browser.findElement(By.id("search_input"));
        searchInput.sendKeys(searchKeyword);

    }
    public static int findSearchResultCount(){
        WebElement searchRezult = browser.findElement(By.id("products_search_total_found_159"));
        String count = searchRezult.getText()
                .replaceAll("[a-zA-Z]", "")
                .replaceAll("[ąčęėįšųūž]", "")
                .replaceAll("[: ]", "");

        return Integer.parseInt(count);
    }

    public static String compareResult(int resultCount){
        if (resultCount == 0) {
            return "Prekiu nerasta";
        }
        return "Prekiu surasta: " + resultCount;
    }

    public static WebElement waitForElementVisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(WAIT_DURATION_SECONDS));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static String getNotification(){
        waitForElementVisibility(By.xpath("//*[@id=\"tygh_container\"]/div[3]/div"));
        return browser.findElement(By.xpath("//*[@id=\"tygh_container\"]/div[3]/div")).getText();
    }

    public static boolean isElementVisible(By locator){
            WebElement element = browser.findElement(locator);
            return element.isDisplayed();
    }

    public static void closeBrowser(){
        browser.quit();
    }

}

