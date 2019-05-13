package samples;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class badSSL implements Runnable {
    public static String userName = "BrowserStack_Username";
    public static String accessKey = "BrowserStack_Key";
    public static List<DesiredCapabilities> capsList=new ArrayList<>();
    public DesiredCapabilities caps;

    public badSSL(DesiredCapabilities caps){
        this.caps=caps;
    }

    @Override
    public void run(){
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + "@hub.browserstack.com/wd/hub"), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        try {
            driver.get("https://self-signed.badssl.com/");
            try{
                driver.findElement(By.xpath("//h1[contains(text(),'self-signed.')]"));
                System.out.println("Session used capability AcceptSSLCerts: "+driver.getCapabilities().getCapability("acceptSslCerts").toString()+" SSL CERT ERROR WAS HANDLED");
            }catch (Exception e){
                System.out.println("Session used capability AcceptSSLCerts: "+driver.getCapabilities().getCapability("acceptSslCerts").toString()+" SSL CERT ERROR WAS NOT HANDLED");
            }
            driver.quit();
        }
        catch(Exception e) {
            System.out.println("AcceptSSLCerts: "+driver.getCapabilities().getCapability("acceptSslCerts").toString()+" threw an error for this session");
            driver.quit();
        }
    }
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
                    DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
                    for(String key:keys){
                        desiredCapabilities.setCapability(key,jsonObject.getString(key));
                    }
                    desiredCapabilities.setCapability("build","Test Build v1.0");
                    capsList.add(desiredCapabilities);

                }
                else if(isJSONArrayValid(jsonToParse)){
                    JSONArray jsonArray = new JSONArray(jsonToParse);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Set<String> keys =jsonObject.keySet();
                        DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
                        for(String key:keys){
                            desiredCapabilities.setCapability(key,jsonObject.getString(key));
                        }
                        desiredCapabilities.setCapability("build","Test Build v1.0");
                        capsList.add(desiredCapabilities);
                    }
                }
                else{
                    System.out.println("Invalid format passed for capabilities");
                    System.exit(1);
                }
            }catch (Exception e){
                System.out.println("Invalid Capabilities passed. Please ensure you are pasting capabilities from https://www.browserstack.com/automate/capabilities > NodeJS dropdown OR similar format");
                System.exit(1);
            }
        }
        else{
            System.out.println("Could not find arguments");
            System.exit(1);
        }

        int threadCount=3;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for ( int i=0;i<capsList.size();i++ ) {
            executor.submit(new badSSL(capsList.get(i)));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("All Tests Completed");
    }

    public static boolean isJSONObjectValid(String capsString) {
        try {
            new JSONObject(capsString);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    public static boolean isJSONArrayValid(String capsString){
        try {
            new JSONArray(capsString);
        } catch (JSONException ex1) {
            return false;
        }
        return true;
    }
}
