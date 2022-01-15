import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Waiter;

import java.util.List;

public class CarvanaTest extends Base {

    @Test(testName = "Validate Carvana home page title and url", priority = 1)
    public void ValidateCarvanaHomePageTitleAndUrl() {
        driver.get("https://www.carvana.com/");
        Assert.assertEquals(driver.getTitle(), "Carvana | Buy & Finance Used Cars Online | At Home Delivery"
        , "Title validation FAILED");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.carvana.com/", "URL validation FAILED");
    }

    @Test(testName = "Validate the Carvana logo", priority = 2)
    public void validateTheCarvanaLogo() {
        driver.get("https://www.carvana.com/");
        Waiter.pause(2);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@data-qa='logo-wrapper']")).isDisplayed(), "Carvana logo display validation FAILED");
    }

    @Test(testName = "Validate the main navigation section items", priority = 3)
    public void validateTheMainNavigationSectionItems() {
        driver.get("https://www.carvana.com/");
        Waiter.waitForWebElementToBeVisible(driver, 10, driver.findElement(By.xpath("//div[@data-qa='menu-title']")));

        List<WebElement> navigationHeaders = driver.findElements(By.xpath("//div[@data-qa='navigation-wrapper']/div/a"));
        String[] actualHeaders = {"HOW IT WORKS", "ABOUT CARVANA", "SUPPORT & CONTACT"};

        for (int i = 0; i < navigationHeaders.size(); i++) {
            Assert.assertTrue(navigationHeaders.get(i).isDisplayed(), "Navigation section items display validation FAILED");
            Assert.assertEquals(navigationHeaders.get(i).getText(), actualHeaders[i]);
        }
    }

    @Test(testName = "Validate the sign in error message", priority = 4)
    public void validateTheSignInErrorMessage() {
        driver.get("https://www.carvana.com/");
        Waiter.waitForWebElementToBeClickable(driver, 10, driver.findElement(By.xpath("//a[@data-cv-test='headerSignInLink']")));
        driver.findElement(By.xpath("//a[@data-cv-test='headerSignInLink']")).click();

        Waiter.waitForWebElementToBeVisible(driver, 10, driver.findElement(By.xpath("//div[@data-cv-test='Header.Modal']")));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@data-cv-test='Header.Modal']")).isDisplayed(), "User navigated to \"Sign in\" modal FAILED");

        List<WebElement> loginInput = driver.findElements(By.xpath("//form[@data-qa='account-modal-forms-wrapper']/div/div/div/input"));
        String[] input = {"johndoe@gmail.com", "abcd1234"};

        for (int i = 0; i <loginInput.size(); i++) {
            loginInput.get(i).sendKeys(input[i]);
        }

        driver.findElement(By.xpath("//button[@data-cv='sign-in-submit']")).click();
        Waiter.waitForWebElementToBeVisible(driver, 10, driver.findElement(By.xpath("//div[@data-qa='error-message-container']")));
        Assert.assertEquals(driver.findElement(By.xpath("//div[@data-qa='error-message-container']")).getText(),
                "Email address and/or password combination is incorrect\nPlease try again or reset your password.");
    }

    @Test(testName = "Validate the search filter options and search button", priority = 5)
    public void validateTheSearchFilterOptionsAndSearchButton() {
        driver.get("https://www.carvana.com/");
        driver.findElement(By.xpath("//a[@data-cv-test='Taxseason.SearchLink']")).click();

        Waiter.waitForURLToBe(driver, 10, "https://www.carvana.com/cars");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.carvana.com/cars", "User directed to right URL validation FAILED");
        Assert.assertTrue(driver.findElement(By.xpath("//input[@data-cv-test='Cv.Search.keywordSearchInput']")).isDisplayed(), "Search input box validation FAILED");

        List<WebElement> filterOptions = driver.findElements(By.xpath("//div[@data-qa='menu-flex']/button"));
        String[] expectedFilterOptions = {"PAYMENT & PRICE", "MAKE & MODEL", "BODY TYPE", "YEAR & MILEAGE", "FEATURES", "MORE FILTERS"};

        for (int i = 0; i < filterOptions.size(); i++) {
            Assert.assertEquals(filterOptions.get(i).getText(), expectedFilterOptions[i]);
        }

        driver.findElement(By.xpath("//input[@data-cv-test='Cv.Search.keywordSearchInput']")).sendKeys("Tesla");
        Assert.assertTrue(driver.findElement(By.xpath("//button[@data-qa='go-button']")).isDisplayed(), "\"GO\" button in the search input box is displayed as expected FAILED");
    }

    @Test(testName = "Validate the search result tiles", priority = 6)
    public void validateTheSearchResultTiles() {
        driver.get("https://www.carvana.com/");
        driver.findElement(By.xpath("//a[@data-cv-test='Taxseason.SearchLink']")).click();

        Waiter.waitForURLToBe(driver, 10, "https://www.carvana.com/cars");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.carvana.com/cars", "User directed to right URL validation FAILED");

        driver.findElement(By.xpath("//input[@data-cv-test='Cv.Search.keywordSearchInput']")).sendKeys("mercedes-benz");
        driver.findElement(By.xpath("//button[@data-qa='go-button']")).click();

        Waiter.waitForURLToBe(driver, 10, "https://www.carvana.com/cars/mercedes-benz?email-capture=");
        Assert.assertTrue(driver.getCurrentUrl().contains("mercedes-benz"), "User should see \"mercedes-benz\" in the url FAILED");

        List<WebElement> tileBody = driver.findElements(By.xpath("//div[@data-test='ResultTile']"));
        List<WebElement> tileImage = driver.findElements(By.xpath("//div[@data-test='ResultTile']/a"));
        List<WebElement> tileFavoriteButton = driver.findElements(By.cssSelector("svg[class='favorite-icon']"));
        List<WebElement> tileInventoryType = driver.findElements(By.xpath("//div[@data-qa='base-inventory-type']"));
        List<WebElement> tileYearMakeModel = driver.findElements(By.xpath("(//div[@data-qa='make-model']//div)"));
        List<WebElement> tileTrimMileageInformation = driver.findElements(By.xpath("//div[@data-qa='trim-mileage']"));
        List<WebElement> tilePrice = driver.findElements(By.xpath("(//div[@class='price-variant '])"));
        List<WebElement> monthlyPayment = driver.findElements(By.xpath("//div[@data-test='MonthlyPayment']//span"));
        List<WebElement> downPayment = driver.findElements(By.xpath("//div[@class='down-payment']"));
        List<WebElement> deliveryChip = driver.findElements(By.xpath("//div[@data-qa='plain-delivery-chip']"));

        for (int i = 0; i <tileBody.size(); i++) {
            Assert.assertTrue(tileBody.get(i).isDisplayed());
            Assert.assertTrue(tileImage.get(i).isDisplayed());
            Assert.assertTrue(tileFavoriteButton.get(i).isDisplayed());
            Assert.assertTrue(tileInventoryType.get(i).isDisplayed());
            Assert.assertFalse(tileInventoryType.get(i).getText().isEmpty());
            Assert.assertTrue(tileYearMakeModel.get(i).isDisplayed());
            Assert.assertFalse(tileYearMakeModel.get(i).getText().isEmpty());
            Assert.assertTrue(tileTrimMileageInformation.get(i).isDisplayed());
            Assert.assertFalse(tileTrimMileageInformation.get(i).getText().isEmpty());
            Assert.assertTrue(tilePrice.get(i).isDisplayed());
            Assert.assertTrue(Integer.parseInt(tilePrice.get(i).getText().replaceAll("[^0-9]", "")) > 0);
            Assert.assertTrue(monthlyPayment.get(i).isDisplayed());
            Assert.assertFalse(monthlyPayment.get(i).getText().isEmpty());
            Assert.assertTrue(downPayment.get(i).isDisplayed());
            Assert.assertFalse(downPayment.get(i).getText().isEmpty());
            Assert.assertEquals(deliveryChip.get(i).getText(), "Free Shipping", "Delivery chip must be displayed as actual text FAILED");
        }
    }
}