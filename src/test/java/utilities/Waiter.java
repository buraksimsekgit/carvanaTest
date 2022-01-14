package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Waiter {
    private static WebDriverWait wait;

    public static void waitForWebElementToBeVisible(WebDriver driver, int seconds, WebElement element){
        wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForWebElementsToBeVisible(WebDriver driver, int seconds, List<WebElement> elements){
        wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public static void waitForWebElementToBeClickable(WebDriver driver, int seconds, WebElement element){
        wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForTitleToBe(WebDriver driver, int seconds, String expectedTitle){
        wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.titleIs(expectedTitle));
    }

    public static void waitForURLToBe(WebDriver driver, int seconds, String expectedURL){
        wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.urlToBe(expectedURL));
    }

    public static void pause(int seconds){
        try{
            Thread.sleep(seconds * 1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}