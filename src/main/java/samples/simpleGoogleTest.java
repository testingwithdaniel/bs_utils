package samples;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import support.setCapabilities;

import junit.framework.Assert;

public class simpleGoogleTest {
    public static String userName = "BrowserStack_Username";
    public static String accessKey = "BrowserStack_Key";

    public static void main(String args[]) throws Exception {
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

        DesiredCapabilities caps = new DesiredCapabilities();
        if(args.length>0) {
            setCapabilities sc= new setCapabilities();
            sc.setDesCaps(args, caps);
        }
        else {
            Assert.assertFalse(true);
        }
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + "@hub.browserstack.com/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        try {
            driver.get("http://www.google.com");
            WebElement element = driver.findElement(By.name("q"));
            element.sendKeys("BrowserStack");
            element.submit();
            System.out.println(driver.getTitle());
            driver.quit();
        }
        catch(Exception e) {
            driver.quit();
            throw new Exception("AcceptSSLCerts: "+driver.getCapabilities().getCapability("acceptSslCerts").toString()+" threw an error for this session");
        }
    }
}