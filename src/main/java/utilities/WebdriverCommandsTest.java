package utilities;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WebdriverCommandsTest {
    
    public void testURL(WebDriver driver) {
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        Assert.assertEquals(driver.getCurrentUrl(),"http://stormy-beyond-9729.herokuapp.com/" );
    }
    
    public void testTitle(WebDriver driver){
        String expectedTitle = "Selenium playground";
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle,expectedTitle);
    }
    
    public void testBackForward(WebDriver driver){
        String expectedUrl = "https://www.facebook.com/";
        driver.get("http://www.browserstack.com");
        driver.get(expectedUrl);
        driver.navigate().back();
        driver.navigate().forward();
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl,expectedUrl);
    }
    
    public void testSendKeys(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String expectedInput = "Testing sendKeys";
        driver.findElement(By.id("q")).sendKeys(expectedInput);
        String actualInput =  driver.findElement(By.id("q")).getAttribute("value");
        Assert.assertEquals(expectedInput,actualInput);
    }
    
    public void testRefresh(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String inputText = "Testing sendKeys";
        driver.findElement(By.id("q")).sendKeys(inputText);
        driver.navigate().refresh();
        String inputAfterRefresh =  driver.findElement(By.id("q")).getAttribute("value");
        Assert.assertTrue(inputAfterRefresh.isEmpty());
    }
    
    public void testGetElementById(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.id("q"));
        String actualValue = element.getAttribute("name");
        String expectedValue = "post[title]";
        Assert.assertEquals(expectedValue,actualValue);
    }
    
    public void testGetElementByCss(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.cssSelector(".ui-draggable"));
        String actualValue = element.getAttribute("style");
        String expectedValue = "position: relative;";
        Assert.assertEquals(expectedValue,actualValue);
    }
    
    public void testGetElementByClassName(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.className("ui-draggable"));
        String actualValue = element.getAttribute("style");
        String expectedValue = "position: relative;";
        Assert.assertEquals(expectedValue,actualValue);
    }

    
    public void testGetImgSrcAttribute(WebDriver driver){
        driver.get("https://www.w3.org");
        WebElement element = driver.findElement(By.cssSelector("div#w3c_mast img"));
        String actualValue = element.getAttribute("src");
        String expectedValue = "https://www.w3.org/2008/site/images/logo-w3c-mobile-lg";
        Assert.assertEquals(expectedValue,actualValue);
    }
    
    public void testGetElementByLinkText(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.linkText("Cheese"));
        Assert.assertTrue(element.isDisplayed());
    }
    
    public void testGetElementByPartialLinkText(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.partialLinkText("Eg"));
        Assert.assertTrue(element.isDisplayed());
    }
    
    public void testGetElementByXpath(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.xpath("//div/p"));
        String actualValue = element.getAttribute("style");
        String expectedValue = "position: relative;";
        Assert.assertEquals(expectedValue,actualValue);
    }
    
    public void testGetElementByName(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.name("post[title]"));
        String actualValue = element.getAttribute("id");
        String expectedValue = "q";
        Assert.assertEquals(expectedValue,actualValue);
    }
    
    public void testGetElementByTagName(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.tagName("link"));
        String actualValue = element.getAttribute("rel");
        String expectedValue = "stylesheet";
        Assert.assertEquals(expectedValue,actualValue);
    }
    
    public void testGetElements(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        List<WebElement> element = driver.findElements(By.cssSelector("#navcontainer ul li"));
        int size = element.size();
        Assert.assertTrue(size==5);
    }
    
    public void testGetElementElements(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        List<WebElement> element = driver.findElement(By.id("navcontainer")).findElements(By.cssSelector("ul li"));
        int size = element.size();
        Assert.assertTrue(size==5);
    }
    
    public void testGetElementElement(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.className("ui-widget-content")).findElement(By.id("draggable"));
        String actualValue = element.getAttribute("style");
        String expectedValue = "position: relative;";
        Assert.assertEquals(expectedValue, actualValue);
    }
    
    public void testClick(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String someInput = "testing";
        driver.findElement(By.id("q")).sendKeys(someInput);
        WebElement element = driver.findElement(By.cssSelector("input[type='submit']"));
        element.click();
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("q")));
        String inputAfterClick =  driver.findElement(By.id("q")).getAttribute("value");
        Assert.assertTrue(inputAfterClick.isEmpty());
    }
    
    public void testClear(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String someInput = "testing";
        driver.findElement(By.id("q")).sendKeys(someInput);
        WebElement element = driver.findElement(By.id("q"));
        element.clear();
        String inputAfterClick =  driver.findElement(By.id("q")).getAttribute("value");
        Assert.assertTrue(inputAfterClick.isEmpty());
    }
    
    public void testSubmit(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String titleBeforeSubmit = "testing";
        driver.findElement(By.id("q")).sendKeys(titleBeforeSubmit);
        WebElement element = driver.findElement(By.cssSelector("input[type='submit']"));
        element.submit();
        String titleAfterSubmit =  driver.getTitle();
        Assert.assertEquals(titleBeforeSubmit,titleAfterSubmit);
    }
    
    public void testGetText(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String expectedText = "Drag & Drop";
        String actualTest = driver.findElement(By.cssSelector("h2")).getText();
        Assert.assertEquals(expectedText,actualTest);
    }
    
    public void testGetTagName(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String expectedTag = "p";
        WebElement element = driver.findElement(By.id("draggable"));
        String actualTag = element.getTagName();
        Assert.assertEquals(expectedTag,actualTag);
    }
    
    public void testGetCssValue(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String expectedCssValue = "16px";
        String actualCssValue = driver.findElement(By.id("draggable")).getCssValue("font-size");
        Assert.assertEquals(expectedCssValue,actualCssValue);
    }
    
    public void testIsSelected(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.cssSelector("select#multiplecars option[value='mercedes']"));
        element.click();
        Assert.assertTrue(element.isSelected());
    }
    
    public void testIsDisplayed(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.id("q"));
        Assert.assertTrue(element.isDisplayed());
    }
    
    public void testIsEnabled(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/test");
        WebElement element = driver.findElement(By.cssSelector("select.select-box"));
        Assert.assertFalse(element.isEnabled());
    }

    
    public  void testSwitchToAlertText(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/test");
        WebElement element = driver.findElement(By.id("alert"));
        element.click();
        String actualAlertText = driver.switchTo().alert().getText();
        Assert.assertEquals("Welcome to selenium playground",actualAlertText);
    }
    
    public  void testAlertAccept(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/test");
        WebElement element = driver.findElement(By.id("alert"));
        element.click();
        driver.switchTo().alert().accept();
    }
    
    public  void testAlertDismiss(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/test");
        WebElement element = driver.findElement(By.id("alert"));
        element.click();
        driver.switchTo().alert().dismiss();
    }
    
    public void testWindowHandles(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        driver.findElement(By.id("openModal")).click();
        Set<String> winHandles = driver.getWindowHandles();
        driver.switchTo().window((String) winHandles.toArray()[winHandles.size()-1]);
        String actualBodyText = driver.findElement(By.cssSelector("body")).getText();
        Assert.assertEquals("You have opened a new window",actualBodyText);
    }
    
    public void testGetWindowHandle(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String handle = driver.getWindowHandle();
        Assert.assertFalse(handle.isEmpty());
    }
    
    public void testWindowSizeAndMaximize(WebDriver driver) {
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        Dimension newSize = new Dimension(100,100);
        driver.manage().window().setSize(newSize);
        Dimension newSizeOfWindow = driver.manage().window().getSize();
        driver.manage().window().maximize();
        Dimension currentSize = driver.manage().window().getSize();
        Assert.assertNotEquals(newSizeOfWindow, currentSize);
    }
    
    public void testWindowPosition(WebDriver driver) {
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        Point positionBefore = driver.manage().window().getPosition();
        driver.manage().window().setPosition(positionBefore.moveBy(200,200));
        Point positionAfter = driver.manage().window().getPosition();
        Assert.assertNotEquals(positionBefore,positionAfter);
        driver.manage().window().maximize();
    }
    
    public void testExecuteScript(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String expectedTitle = "Selenium playground";
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        String actualTitle = ((String)js.executeScript("return document.title"));
        Assert.assertEquals(expectedTitle,actualTitle);
    }
    
    public void testExecuteAsyncScript(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String expectedTitle = "Selenium playground";
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        String actualTitle = ((String)js.executeScript("return document.title"));
        Assert.assertEquals(expectedTitle,actualTitle);
    }
    
    public void testPageSource(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String pageSource = driver.getPageSource();
        Assert.assertNotNull(pageSource);
    }
    
    public void testActiveElement(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        String input = "Testing";
        WebElement element = driver.findElement(By.id("q"));
        element.sendKeys(input);
        element = driver.switchTo().activeElement();
        element.clear();
        String finalInput =  driver.findElement(By.id("q")).getAttribute("value");
        Assert.assertTrue(finalInput.isEmpty());
    }
    
    public void testElementLocation(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.id("q"));
        Point location = element.getLocation();
        Dimension dim = element.getSize();
        Assert.assertTrue(location.getX()>0);
        Assert.assertTrue(location.getY()>0);
    }
    
    public void testElementSize(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.id("q"));
        Dimension dim = element.getSize();
        Assert.assertTrue(dim.getHeight()>0);
        Assert.assertTrue(dim.getWidth()>0);
    }
    
    public void testMoveToElement(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/test");
        WebElement element = driver.findElement(By.id("hoverme"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        String actualHoverText = driver.findElement(By.id("hovertext")).getText();
        Assert.assertEquals("Hover in",actualHoverText);
    }
    
    public void testClickAndHold(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.cssSelector("input[type='submit']"));
        Actions actions = new Actions(driver);
        actions.clickAndHold(element).build().perform();
        actions.moveToElement(element).clickAndHold().build().perform();
    }
    
    public void testActionClick(WebDriver driver) {
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.cssSelector("input[type='submit']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
        WebDriverWait wait = new WebDriverWait(driver,4);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='submit']")));
        element = driver.findElement(By.cssSelector("input[type='submit']"));
        actions.click(element).build().perform();
    }
    
    public void testDragAndDrop(WebDriver driver) {
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.id("draggable"));
        Point intialPosition = element.getLocation();
        WebElement destElement = driver.findElement(By.cssSelector("input[type='submit']"));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(element, destElement).build().perform();
        Point finalPosition = driver.findElement(By.id("draggable")).getLocation();
        Assert.assertNotEquals(intialPosition,finalPosition);
        intialPosition = finalPosition;
        actions.dragAndDropBy(destElement,10,10).build().perform();
        finalPosition = driver.findElement(By.id("draggable")).getLocation();
        Assert.assertNotEquals(intialPosition,finalPosition);
    }
    
    public void testContextClickAtElement(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.id("q"));
        Actions action = new Actions(driver);
        action.contextClick(element).build().perform();
    }
    
    public void testContextClick(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.id("q"));
        Actions action = new Actions(driver);
        action.contextClick().build().perform();
    }
    
    public void testKeyDownKeyUp(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        Actions builder = new Actions(driver);
        WebElement element1 = driver.findElement(By.linkText("Milk"));
        WebElement element2 = driver.findElement(By.linkText("Eggs"));
        builder.keyDown(Keys.CONTROL).click(element1).click(element2).keyUp(Keys.CONTROL).build().perform();
    }
    
    public void testHoldAndRelease(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        Actions builder = new Actions(driver);
        WebElement element1 = driver.findElement(By.id("draggable"));
        WebElement element2 = driver.findElement(By.id("q"));
        builder.clickAndHold(element1).moveToElement(element2).release().build().perform();
    }
    
    public void testActionSendKeys(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = driver.findElement(By.id("q"));
        Actions action = new Actions(driver);
        String expectedInput = "Some Keys";
        action.sendKeys(element,expectedInput).build().perform();
        String actualInput = element.getAttribute("value");
        Assert.assertEquals(expectedInput,actualInput);
    }
    
    public void testScreenshot(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
    }
    
    public void testImplicitWait(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        driver.findElement(By.id("q"));
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }
    
    public void testExplicitWait(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
    }
    
    public void testAddCookieAndGetCookie(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        Cookie expectedCookie = new Cookie("cookie","coo");
        driver.manage().addCookie(expectedCookie);
        Cookie actualCookie = driver.manage().getCookieNamed("cookie");
        Assert.assertEquals(expectedCookie,actualCookie);
    }
    
    public void testGetAllCookies(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        Cookie newCookie = new Cookie("cookie","coo");
        driver.manage().addCookie(newCookie);
        Set allCookies = driver.manage().getCookies();
        Assert.assertTrue(allCookies.contains(newCookie));
    }
    
    public void testDeleteCookieNamed(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        Cookie newCookie = new Cookie("cookie","coo");
        driver.manage().addCookie(newCookie);
        driver.manage().deleteCookieNamed("cookie");
        Set allCookies = driver.manage().getCookies();
        Assert.assertFalse(allCookies.contains(newCookie));
    }
    
    public void testDeleteAllCookies(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        Cookie newCookie = new Cookie("cookie","coo");
        driver.manage().addCookie(newCookie);
        driver.manage().deleteAllCookies();
        Set allCookies = driver.manage().getCookies();
        Assert.assertFalse(allCookies.contains(newCookie));
    }
    
    public void testSwitchToFrame(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/frame");
        driver.switchTo().frame("menu");
        WebElement element = driver.findElement(By.cssSelector("a[href='sample.html']"));
        Assert.assertTrue(element.isDisplayed());
    }
    
    public void testSwitchToDefaultContent(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/frame");
        driver.switchTo().frame("menu");
        WebElement element = driver.findElement(By.cssSelector("a[href='sample.html']"));
        Assert.assertTrue(element.isDisplayed());
        driver.switchTo().defaultContent();
        try {
            driver.findElement(By.cssSelector("a[href='sample.html']"));
        }
        catch (NoSuchElementException e)
        {
            String str = e.getMessage();
            Assert.assertTrue(str.contains("no such element"));
        }
    }
    
    public void testCloseCurrentWindow(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        driver.close();
        try {
            driver.get("http://stormy-beyond-9729.herokuapp.com/");
        }
        catch (Exception e)
        {
            String str = e.getMessage();
            Assert.assertTrue(str.contains("no such session"));
        }
    }
    
    public void testDoubleClick(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/test");
        Actions actions = new Actions(driver);
        actions.doubleClick(driver.findElement(By.cssSelector("div#doubleclick"))).build().perform();
        String randomText = driver.findElement(By.cssSelector("div#random1")).getText();
        Assert.assertFalse(randomText.isEmpty());
    }
    
    public void testGetLocalStorageKey(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        String storageValue = js.executeScript("return window.localStorage.getItem('firstname');").toString();
        Assert.assertEquals("Jason", storageValue);
    }
    
    public void testClearLocalStorage(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.localStorage.clear();");
        String storageValue = (String)(js.executeScript("return window.localStorage.getItem('firstname');"));
        Assert.assertNull(storageValue);
    }
    
    public void testLocalStorageSize(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.localStorage.clear();");
        String storageValueLength = js.executeScript("return window.localStorage.length;").toString();
        Assert.assertTrue(storageValueLength.matches("0"));
    }
    
    public void testWaitForElementVisiblity(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/test");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delaytext")));
        Assert.assertTrue(element.isDisplayed());
    }
    
    public void testControlKeyDown(WebDriver driver){
        driver.get("http://stormy-beyond-9729.herokuapp.com/");
        Actions action = new Actions(driver);
        WebElement element1 = driver.findElement(By.cssSelector("#multiplecars option[value='volvo']"));
        WebElement element2 = driver.findElement(By.cssSelector("#multiplecars option[value='mercedes']"));
        action.keyDown(Keys.CONTROL).build().perform();
        element1.click();
        element2.click();
        action = new Actions(driver);
        action.keyUp(Keys.CONTROL).build().perform();
        driver.findElement(By.id("getcars")).click();
        String selected = driver.findElement(By.id("selectedcars")).getText();
        Assert.assertEquals("Volvo\nMercedes",selected);
    }
    
    public void testFileUpload(WebDriver driver){
        String text = "Hello World";
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.get("http://stormy-beyond-9729.herokuapp.com/upload");
        File f = null;
        String path = null;
        try{
            f = new File("./sampleFiles/file_upload.txt");
            path = f.getAbsolutePath();
        }catch(Exception e){
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("input[name='myfile']")).sendKeys(path);
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        text = driver.findElement(By.tagName("body")).getText();
        Assert.assertEquals("Hello World",text);
    }
}
