import org.example.Filmai;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

public class FilmaiTest {
    public static String id;

    @BeforeAll
    public static void setUp(){
        Filmai.setup();
    }

    @Test
    @Order(1)
    public void createNewMovie(){
        Filmai.fillMovieForm(Filmai.MOVIE_NAME);
        Filmai.clickOnElement(By.name("insert"));
        String outCome = Filmai.getNotificationMessage();
        Assertions.assertEquals("Duomenys įrašyti sėkmingai", outCome);
        id = Filmai.findMovieId();
    }

    @Test
    @Order(2)
    public void createNewMovieNegative(){
        Filmai.fillMovieForm("");
        Filmai.clickOnElement(By.name("insert"));
        String outCome = Filmai.getNotificationMessage();
        Assertions.assertEquals("Duomenų įvedimo klaida", outCome);
    }

    @Test
    @Order(3)
    public void editMovie(){
        Filmai.fillIdField(id);
        Filmai.fillMovieForm(Filmai.MOVIE_NAME2);
        Filmai.clickOnElement(By.name("update"));
        String outCome = Filmai.getNotificationMessage();
        Assertions.assertEquals("Įrašas paredaguotas sėkmingai", outCome);
    }

    @Test
    @Order(4)
    public void editMovieNegative(){
        Filmai.fillIdField(id);
        Filmai.fillMovieForm("");
        Filmai.clickOnElement(By.name("update"));
        String outCome = Filmai.getNotificationMessage();
        Assertions.assertEquals("Blogai įvesti duomenys redaguojant įrašą", outCome);
    }

    @Test
    @Order(5)
    public void deleteMovie(){
        Filmai.fillIdField(id);
        Filmai.clickOnElement(By.name("delete"));
        String outCome = Filmai.getNotificationMessage();
        Assertions.assertEquals("Įrašas ištrintas sėkmingai", outCome);
    }

    @AfterAll
    public static void close(){
        Filmai.closeBrowser();
    }
}
