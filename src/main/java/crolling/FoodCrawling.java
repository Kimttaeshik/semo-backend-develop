package crolling;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;


@Component
@Slf4j
@Transactional
public class FoodCrawling {
    private final WebDriver webDriver;

    private WebElement element;
    private String url = "https://portal.sejong.ac.kr/jsp/login/loginSSL.jsp";

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
        chromeOptions.addArguments("--headless"); // 최신 Selenium에서 --headless=new 사용
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");

        // 자동화 탐지 방지
        chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");



        webDriver = new ChromeDriver(chromeOptions);

    }


    /*public void activateBot() {
        try {

            webDriver.get(url);
            // 2. 로그인 과정
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

            // 아이디와 비밀번호 필드를 찾기
            WebElement idField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id"))); // 아이디 입력 필드의 ID 속성
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password"))); // 비밀번호 입력 필드의 ID 속성


            // 아이디와 비밀번호 입력
            idField.sendKeys("19010668"); // 실제 사용자 아이디
            passwordField.sendKeys("rlaxotlr3220"); // 실제 사용자 비밀번호

            // 로그인 버튼 클릭
            WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loginBtn")));
            loginButton.click();

            // 로그인 완료를 기다림 (로그인 후 URL 포함을 기다림)
            wait.until(ExpectedConditions.urlContains("https://portal.sejong.ac.kr/user/index.do")); // 로그인 후 페이지 URL이 포함되기를 기다림

            // 로그인 후 알림을 처리하는 부분
            try {
                // 알림이 나타날 때까지 기다림
                WebDriverWait alertWait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
                alertWait.until(ExpectedConditions.alertIsPresent());

                // 알림 처리
                Alert alert = webDriver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("알림 텍스트: " + alertText);

                // 알림 닫기 (또는 수락)
                alert.dismiss(); // 알림을 닫기, alert.accept()로 수락할 수도 있음
            } catch (NoAlertPresentException e) {
                System.out.println("알림 없음");
            }

            // 3. 로그인 후 크롤링할 요소 추출 (예시: 메뉴 항목 또는 특정 정보)
            WebElement menuElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[style*='border-bottom:1px solid']"))); // 예시: CSS Selector 사용

            // 4. 텍스트 추출 (반찬을 제외한 주요 메뉴만 추출)
            String menuText = menuElement.getText();

            // 5. '·'를 기준으로 반찬을 제외한 부분만 가져오기
            String[] items = menuText.split("·");  // '·' 기호로 분리
            String mainMenu = items[0].trim(); // 첫 번째 항목이 주요 메뉴

            // 6. 결과 출력
            log.info("주요 메뉴: " + mainMenu);


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // 드라이버 종료
            if (webDriver != null) {
                webDriver.quit();
            }
        }*/

    public void activateBot() {
        try {
            log.info("Step 1: Navigating to the login page.");
            webDriver.get(url); // 로그인 페이지로 이동

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

            log.info("Step 2: Waiting for the ID field to be present.");
            WebElement idField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id"))); // 아이디 입력 필드의 ID 속성

            log.info("Step 3: Waiting for the password field to be present.");
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password"))); // 비밀번호 입력 필드의 ID 속성


            log.info("Step 4: Entering user credentials.");
            idField.click();
            idField.sendKeys("19010668"); // 실제 사용자 아이디
            passwordField.click();
            passwordField.sendKeys("rlaxotlr3220"); // 실제 사용자 비밀번호

            log.info("Step 5: Waiting for login button to be present.");
            WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loginBtn")));

            log.info("Step 6: Clicking login button.");
            loginButton.click();

            log.info("Step 7: Waiting for successful login (waiting for URL change).");
            wait.until(ExpectedConditions.urlContains("https://portal.sejong.ac.kr/user/index.do")); // 로그인 후 페이지 URL이 포함되기를 기다림

            log.info("Step 8: Checking for any alert.");
            try {
                WebDriverWait alertWait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
                alertWait.until(ExpectedConditions.alertIsPresent());
                log.info("Step 9: Alert found, handling alert.");
                Alert alert = webDriver.switchTo().alert();
                alert.dismiss(); // 알림을 닫기
            } catch (NoAlertPresentException e) {
                log.info("Step 9: No alert present.");
            }

            log.info("Step 10: Waiting for the menu element to be present.");
            WebElement menuElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[style*='border-bottom:1px solid']"))); // 예시: CSS Selector 사용

            log.info("Step 11: Extracting menu text.");
            String menuText = menuElement.getText();

            log.info("Step 12: Splitting menu text to get the main menu.");
            String[] items = menuText.split("·");  // '·' 기호로 분리
            String mainMenu = items[0].trim(); // 첫 번째 항목이 주요 메뉴

            log.info("Step 13: Main menu extracted: " + mainMenu);

        } catch (Exception e) {
            log.error("Error: ", e);
        } finally {
            // 드라이버 종료
            if (webDriver != null) {
                webDriver.quit();
                log.info("Step 14: Web driver closed.");
            }
        }
    }



    public static void main(String[] args) throws IOException, InterruptedException {
        FoodCrawling bot = new FoodCrawling();
        bot.activateBot();
    }
}
