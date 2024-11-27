package crolling;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;


@Component
@Slf4j
@Transactional
public class FoodCrawling {
    private final WebDriver webDriver;

    private WebElement element;
    private String url = "https://portal.sejong.ac.kr/user/index.do";

    //property 값
    public FoodCrawling() throws InterruptedException, IOException {
        String os = System.getProperty("os.name").toLowerCase();

        // 안되면 절대경로로 하세요
        if (os.contains("win")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\kts11\\Downloads\\chromedriver-win32\\chromedriver.exe");
        } else if (os.contains("mac")) {
            Process process = Runtime.getRuntime().exec("xattr -d com.apple.quarantine ./drivers/chromedriver");
            process.waitFor();
            System.setProperty("webdriver.chrome.driver", "/Users/chaeyun/Documents/school/semo/src/main/resources/drivers/chromedriver");
        } else if (os.contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "/drivers/chromedriver_linux");
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless=new"); // 최신 Selenium에서 --headless=new 사용
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");


        webDriver = new ChromeDriver(chromeOptions);

    }


    public void activateBot() {
        try {
            webDriver.get(url);
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            WebElement menuElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[style*='border-bottom:1px solid']")));
            String menuText = menuElement.getText();
            log.info("식단: " + menuText);

            log.info("식단: " + menuText);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 드라이버 종료
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        FoodCrawling bot = new FoodCrawling();
        bot.activateBot();
    }
}
