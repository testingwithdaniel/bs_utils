package utilities;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class checkChromeVerMobiles implements Runnable {
    public static String accessKey = "BrowserStack_Key";
    public static String userName = "BrowserStack_Username";

    //Specify Parallel Thread count
    public static int threads=0;
    public static Map<Integer,String> androidBrowsers= new HashMap<>();
    public static String URLs = "https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
    private String capability;

    public checkChromeVerMobiles(String capability){
        this.capability=capability;
    }

    @Override
    public void run(){
        JSONObject jobj = new JSONObject(capability);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        Set<String> keys = jobj.keySet();
        for (String s : keys) {
            if((jobj.get(s)!=null) && !(jobj.get(s).toString().equalsIgnoreCase("null")))
                desiredCapabilities.setCapability(s,jobj.get(s));
        }
        desiredCapabilities.setCapability("build","Android-Chrome Version Test");
        AndroidDriver driver=null;
        URLs = "http://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
        try {
            driver = new AndroidDriver(new URL(URLs), desiredCapabilities);
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.get("https://www.whatismybrowser.com");
            System.out.println(jobj.get("device")+" : "+driver.findElement(By.xpath("//div[@class='string-major']/a")).getText()+" : "+jobj.get("os_version"));
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
            if(driver!=null){
                driver.quit();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
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
        try{
            threads=Integer.valueOf(args[0]);
            if(threads==0 || threads < 0){
                System.out.println("Invalid Thread Count - Thread count cannot be less than or equal to zero");
                System.exit(1);
            }
            else if(threads>5){
                System.out.println("Thread Count cannot be greator than 5");
                System.exit(1);
            }
        }catch (Exception e){
            System.out.println("Invalid Thread Count");
            System.exit(1);
        }
        try {
            URL url = new URL("https://api.browserstack.com/automate/browsers.json");
            String authStr = userName+":"+accessKey;
            // encode data on your side using BASE64
            byte[] bytesEncoded = Base64.encodeBase64(authStr .getBytes());
            String authEncoded = new String(bytesEncoded);
            //HttpConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic "+authEncoded);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            //Check if the response is valid
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            else if(conn.getResponseCode() == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output,jsonString = "";
                while ((output = br.readLine()) != null) {
                    jsonString=output;
                    //System.out.println(output);
                }
                JSONArray jsonArray = new JSONArray(jsonString);
                int numberofDevices=0;
                if(jsonArray.length()>1) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj=jsonArray.getJSONObject(i);
                        if((jobj.get("os").equals("android")) && (jobj.get("real_mobile").toString().equalsIgnoreCase("true"))){
                            androidBrowsers.put(numberofDevices,jobj.toString());
                            numberofDevices++;
                        }
                    }
                    int threadCount=threads;
                    System.out.println("Running Parallel Threads: "+threadCount);
                    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
                    for ( int i=0;i<androidBrowsers.size();i++ ) {
                        executor.submit(new checkChromeVerMobiles(androidBrowsers.get(i)));
                    }
                    executor.shutdown();
                    while (!executor.isTerminated()) {
                    }
                    System.out.println("All Tests Completed");
                }
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}