import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.time.Duration;

public class MainTest {

    private PracticeFormPage practiceFormPage;

    @BeforeEach
    public void setupDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\auto\\driver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        practiceFormPage = new PracticeFormPage(driver);
    }

    @Test
    public void testForm() {
        practiceFormPage.navigateToForm();
        practiceFormPage.fillForm();
        practiceFormPage.submitForm();
    }

    @AfterEach
    public void tearDown() {
        practiceFormPage.tearDown();
    }
}

class PracticeFormPage {

    private WebDriver driver;

    public PracticeFormPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToForm() {
        driver.get("https://demoqa.com/automation-practice-form");
    }


    public void fillForm() {
        WebElement firstName = driver.findElement(By.xpath("//input[@placeholder='First Name']")); // обращение к FN

        WebElement lastName = driver.findElement(By.xpath("//input[@placeholder='Last Name']")); // обращение к LN

        WebElement email = driver.findElement(By.id("userEmail")); // обращение к email

        WebElement gender = driver.findElement(By.xpath("//label[@for='gender-radio-1']")); // обращение к gender

        WebElement phone = driver.findElement(By.cssSelector("#userNumber")); // обращение к phone

        driver.findElement(By.id("dateOfBirthInput")).click(); // обращение к birth
        driver.findElement(By.cssSelector("[aria-label='Choose Monday, January 29th, 2024']")).click();

        WebElement subject = driver.findElement(By.id("subjectsInput")); // обращение к subject

        WebElement file = driver.findElement(By.cssSelector("input[type=file]")); // обращение к file

        WebElement address = driver.findElement(By.id("currentAddress")); // обращение к address

        WebElement state = driver.findElement(By.id("state")); // обращение к state

        WebElement city = driver.findElement(By.id("city")); // обращение к city

        firstName.sendKeys("fiv");
        lastName.sendKeys("junior");
        email.sendKeys("qa@mail.ru");
        gender.click();

        phone.sendKeys("9111232312");
        subject.sendKeys("m");

        WebElement subjectel = (new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#react-select-2-option-0")))); //используем неявное ожидание

        subjectel.click();

        File uploadFile = new File("src/main/resources/me.jpeg");

        file.sendKeys(uploadFile.getAbsolutePath());

        address.sendKeys("Fair enough, 13");

        // в рамках state
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", state); // скролл вниз

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        state.click();

        driver.findElement(By.cssSelector("#react-select-3-option-0")).click();

        // в рамках city
        city.click();

        driver.findElement(By.cssSelector("#react-select-4-option-0")).click();

        //удаляем футер
        WebElement footer = driver.findElement(By.xpath("//footer"));
        WebElement helper = driver.findElement(By.xpath("//a[@id='close-fixedban']"));

        ((JavascriptExecutor)driver).executeScript("arguments[0].remove();", footer);
        ((JavascriptExecutor)driver).executeScript("arguments[0].remove();", helper);
    }

    public void submitForm() {

        WebElement submit = driver.findElement(By.xpath("//button[@id='submit']"));
        submit.click();
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


