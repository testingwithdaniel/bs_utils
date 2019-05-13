package samples;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.WebdriverCommandsTest;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class runCustomMethod {
    public static String userName = "BrowserStack_Username";
    public static String accessKey = "BrowserStack_Key";
    public static void main(String args[]){
        DesiredCapabilities desiredCapabilities = null;
        String methodToRun="";
        try{
            userName=System.getenv("BROWSERSTACK_USERNAME");
            accessKey=System.getenv("BROWSERSTACK_KEY");
            if(userName.length()<1 || accessKey.length() <1 || userName.contains("BrowserStack") || accessKey.contains("BrowserStack")){
                System.out.println("Invalid Username or Access Key");
                System.exit(1);
            }

        }catch (Exception e){
            System.out.println("Invalid Username or Access Key");
            System.exit(1);
        }

        if(args.length>0){
            try{
                String jsonToParse=args[0].trim();
                if(jsonToParse.contains("var capabilities =")) {
                    jsonToParse = jsonToParse.replaceAll("var capabilities =", "").trim();
                }
                if(jsonToParse.contains("'")) {
                    jsonToParse = jsonToParse.trim().replaceAll("'", "\"");
                }
                if(jsonToParse.contains("\t")){
                    jsonToParse = jsonToParse.replaceAll("\t", "");
                }
                if(jsonToParse.contains("\n")){
                    jsonToParse = jsonToParse.replaceAll("\n", "");
                }
                if(isJSONObjectValid(jsonToParse)){
                    JSONObject jsonObject = new JSONObject(jsonToParse);
                    Set<String> keys =jsonObject.keySet();
                    desiredCapabilities=new DesiredCapabilities();
                    for(String key:keys){
                        desiredCapabilities.setCapability(key,jsonObject.getString(key));
                    }
                    desiredCapabilities.setCapability("build","Test Build v1.0");
                }
                else{
                    System.out.println("Capabilities missing or passed in an invalid format");
                    System.exit(1);
                }
            }catch (Exception e){
                System.out.println("Invalid Capabilities passed. Please ensure you are pasting capabilities from https://www.browserstack.com/automate/capabilities > NodeJS dropdown OR similar format");
                System.exit(1);
            }
            try{
                methodToRun="test"+args[1].trim();
                desiredCapabilities.setCapability("name", args[1].trim());
                Class c = WebdriverCommandsTest.class;
                Method[] methods = c.getDeclaredMethods();             
                for (int i = 0; i < methods.length; i++){
                    if(methods[i].getName().equals(methodToRun)){
                        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + "@hub.browserstack.com/wd/hub"), desiredCapabilities);
                        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                        WebdriverCommandsTest wdcommands = new WebdriverCommandsTest();
                        switch (methodToRun) {
                            case "testURL": {
                                wdcommands.testURL(driver);
                                break;
                            }
                            case "testTitle":{
                                wdcommands.testTitle(driver);
                                break;
                            }
                            case "testBackForward":{
                                wdcommands.testBackForward(driver);
                                break;
                            }
                            case "testSendKeys":{
                                wdcommands.testSendKeys(driver);
                                break;
                            }
                            case "testRefresh":{
                                wdcommands.testRefresh(driver);
                                break;
                            }
                            case "testGetElementById":{
                                wdcommands.testGetElementById(driver);
                                break;
                            }
                            case "testGetElementByCss":{
                                wdcommands.testGetElementByCss(driver);
                                break;
                            }
                            case "testGetElementByClassName":{
                                wdcommands.testGetElementByClassName(driver);
                                break;
                            }
                            case "testGetImgSrcAttribute":{
                                wdcommands.testGetImgSrcAttribute(driver);
                                break;
                            }
                            case "testGetElementByLinkText":{
                                wdcommands.testGetElementByLinkText(driver);
                                break;
                            }
                            case "testGetElementByPartialLinkText":{
                                wdcommands.testGetElementByPartialLinkText(driver);
                                break;
                            }
                            case "testGetElementByXpath":{
                                wdcommands.testGetElementByXpath(driver);
                                break;
                            }
                            case "testGetElementByName":{
                                wdcommands.testGetElementByName(driver);
                                break;
                            }
                            case "testGetElementByTagName":{
                                wdcommands.testGetElementByTagName(driver);
                                break;
                            }
                            case "testGetElements":{
                                wdcommands.testGetElements(driver);
                                break;
                            }
                            case "testGetElementElements":{
                                wdcommands.testGetElementElements(driver);
                                break;
                            }
                            case "testGetElementElement":{
                                wdcommands.testGetElementElement(driver);
                                break;
                            }
                            case "testClick":{
                                wdcommands.testClick(driver);
                                break;
                            }
                            case "testClear":{
                                wdcommands.testClear(driver);
                                break;
                            }
                            case "testSubmit":{
                                wdcommands.testSubmit(driver);
                                break;
                            }
                            case "testGetText":{
                                wdcommands.testGetText(driver);
                                break;
                            }
                            case "testGetTagName":{
                                wdcommands.testGetTagName(driver);
                                break;
                            }
                            case "testGetCssValue":{
                                wdcommands.testGetCssValue(driver);
                                break;
                            }
                            case "testIsSelected":{
                                wdcommands.testIsSelected(driver);
                                break;
                            }
                            case "testIsDisplayed":{
                                wdcommands.testIsDisplayed(driver);
                                break;
                            }
                            case "testIsEnabled":{
                                wdcommands.testIsEnabled(driver);
                                break;
                            }
                            case "testSwitchToAlertText":{
                                wdcommands.testSwitchToAlertText(driver);
                                break;
                            }
                            case "testAlertAccept":{
                                wdcommands.testAlertAccept(driver);
                                break;
                            }
                            case "testAlertDismiss":{
                                wdcommands.testAlertDismiss(driver);
                                break;
                            }
                            case "testWindowHandles":{
                                wdcommands.testWindowHandles(driver);
                                break;
                            }
                            case "testGetWindowHandle":{
                                wdcommands.testGetWindowHandle(driver);
                                break;
                            }
                            case "testWindowSizeAndMaximize":{
                                wdcommands.testWindowSizeAndMaximize(driver);
                                break;
                            }
                            case "testWindowPosition":{
                                wdcommands.testWindowPosition(driver);
                                break;
                            }
                            case "testExecuteScript":{
                                wdcommands.testExecuteScript(driver);
                                break;
                            }
                            case "testExecuteAsyncScript":{
                                wdcommands.testExecuteAsyncScript(driver);
                                break;
                            }
                            case "testPageSource":{
                                wdcommands.testPageSource(driver);
                                break;
                            }
                            case "testActiveElement":{
                                wdcommands.testActiveElement(driver);
                                break;
                            }
                            case "testElementLocation":{
                                wdcommands.testElementLocation(driver);
                                break;
                            }
                            case "testElementSize":{
                                wdcommands.testElementSize(driver);
                                break;
                            }
                            case "testMoveToElement":{
                                wdcommands.testMoveToElement(driver);
                                break;
                            }
                            case "testClickAndHold":{
                                wdcommands.testClickAndHold(driver);
                                break;
                            }
                            case "testActionClick":{
                                wdcommands.testActionClick(driver);
                                break;
                            }
                            case "testDragAndDrop":{
                                wdcommands.testDragAndDrop(driver);
                                break;
                            }
                            case "testContextClickAtElement":{
                                wdcommands.testContextClickAtElement(driver);
                                break;
                            }
                            case "testContextClick":{
                                wdcommands.testContextClick(driver);
                                break;
                            }
                            case "testKeyDownKeyUp":{
                                wdcommands.testKeyDownKeyUp(driver);
                                break;
                            }
                            case "testHoldAndRelease":{
                                wdcommands.testHoldAndRelease(driver);
                                break;
                            }
                            case "testActionSendKeys":{
                                wdcommands.testActionSendKeys(driver);
                                break;
                            }
                            case "testScreenshot":{
                                wdcommands.testScreenshot(driver);
                                break;
                            }
                            case "testImplicitWait":{
                                wdcommands.testImplicitWait(driver);
                                break;
                            }
                            case "testExplicitWait":{
                                wdcommands.testExplicitWait(driver);
                                break;
                            }
                            case "testAddCookieAndGetCookie":{
                                wdcommands.testAddCookieAndGetCookie(driver);
                                break;
                            }
                            case "testGetAllCookies":{
                                wdcommands.testGetAllCookies(driver);
                                break;
                            }
                            case "testDeleteCookieNamed":{
                                wdcommands.testDeleteCookieNamed(driver);
                                break;
                            }
                            case "testDeleteAllCookies":{
                                wdcommands.testDeleteAllCookies(driver);
                                break;
                            }
                            case "testSwitchToFrame":{
                                wdcommands.testSwitchToFrame(driver);
                                break;
                            }
                            case "testSwitchToDefaultContent":{
                                wdcommands.testSwitchToDefaultContent(driver);
                                break;
                            }
                            case "testCloseCurrentWindow":{
                                wdcommands.testCloseCurrentWindow(driver);
                                break;
                            }
                            case "testDoubleClick":{
                                wdcommands.testDoubleClick(driver);
                                break;
                            }
                            case "testGetLocalStorageKey":{
                                wdcommands.testGetLocalStorageKey(driver);
                                break;
                            }
                            case "testClearLocalStorage":{
                                wdcommands.testClearLocalStorage(driver);
                                break;
                            }
                            case "testLocalStorageSize":{
                                wdcommands.testLocalStorageSize(driver);
                                break;
                            }
                            case "testWaitForElementVisiblity":{
                                wdcommands.testWaitForElementVisiblity(driver);
                                break;
                            }
                            case "testControlKeyDown":{
                                wdcommands.testControlKeyDown(driver);
                                break;
                            }
                            case "testFileUpload":{
                                wdcommands.testFileUpload(driver);
                                break;
                            }
                            default: {
                                System.out.println("Could not match any methods");
                                break;
                            }
                        }
                        driver.quit();
                        break;
                    }
                }
            }catch (Exception e){
                System.out.println("Method you supplied to run does not exist");
                e.printStackTrace();
                System.exit(1);
            }
        }
        else{
            System.out.println("Please ensure you are passing capabilities and a method name as argument");
        }
    }

    public static boolean isJSONObjectValid(String capsString) {
        try {
            new JSONObject(capsString);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }
}