import org.example.Baldai1;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

public class Baldai1Test {
    public static final String EMAIL = Baldai1.generateUniqueEmail();

    @BeforeAll
    public static void setUp(){
        Baldai1.setup();
        Baldai1.closeCookies();

    }

    @Test
    @Order(1)
    public void registrationSuccessful(){
        Baldai1.clickUserIcon();
        Baldai1.clickRegisterButton();
        Baldai1.fillRegistrationForm(EMAIL, true);
        Baldai1.clickAgreementCheckBox();
        Baldai1.submitRegisterForm();

        String result = Baldai1.getNotification();
        Assertions.assertEquals("×\n" +
                "Informacija Pirkėjo sąskaita sėkmingai sukurta.", result);
        Baldai1.clickUserIcon();
        Baldai1.clickLogoutButton();
    }

    @Test
    @Order(4)
    public void registrationNegative(){
        Baldai1.clickUserIcon();
        Baldai1.clickRegisterButton();
        Baldai1.fillRegistrationForm(EMAIL, false);
        Baldai1.clickAgreementCheckBox();
        Baldai1.submitRegisterForm();

        String result = Baldai1.browser.findElement(By.xpath("//*[@id=\"elm_6_error_message\"]/p")).getText();
        Assertions.assertEquals("Vardas laukas privalomas", result);
    }

    @Test
    @Order(2)
    public void userLoginSuccessful(){
        Baldai1.clickUserIcon();
        Baldai1.clickLoginButton();
        Baldai1.fillLoginForm(EMAIL);
        Baldai1.clickLoginFormButton();

        String result = Baldai1.getNotification();
        Assertions.assertEquals("×\n" +
                "Dėmesio Jūs sėkmingai prisijungėte.", result);
        Baldai1.clickUserIcon();
        Baldai1.clickLogoutButton();
    }

    @Test
    @Order(5)
    public void userLoginSuccessfulNegative(){
        Baldai1.clickUserIcon();
        Baldai1.clickLogoutButton();
        Baldai1.clickUserIcon();
        Baldai1.clickLoginButton();
        Baldai1.fillLoginForm("blablabla@bla.com");
        Baldai1.clickLoginFormButton();

        String result = Baldai1.getNotification();
        Assertions.assertEquals("×\n" +
                "Klaida Įvedėte vartotojo vardą arba slaptažodį kuris yra neteisingas. Bandykite dar kartą.", result);
    }

    @Test
    @Order(3)
    public void searchReturnsResult(){
        Baldai1.clickOnElement(By.id("search_input"));
        Baldai1.searchInput(Baldai1.SEARCH_KEYWORD);
        Baldai1.clickOnElement(By.className("ty-search-magnifier"));

        int resultCount = Baldai1.findSearchResultCount();
        Assertions.assertTrue(resultCount > 0);

        Baldai1.browser.findElement(By.id("search_input")).clear();
    }

    @Test
    @Order(6)
    public void searchReturnsResultNegative(){
        Baldai1.clickOnElement(By.id("search_input"));
        Baldai1.searchInput("blablabla");
        Baldai1.clickOnElement(By.className("ty-search-magnifier"));

        int resultCount = Baldai1.findSearchResultCount();
        Assertions.assertEquals(resultCount, 0);

        Baldai1.browser.findElement(By.id("search_input")).clear();
    }

    @AfterAll
    public static void close(){
        Baldai1.closeBrowser();
    }
}
