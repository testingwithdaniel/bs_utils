package utilities;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class latestBrowsers {
    public static String userName = "BrowserStack_Username";
    public static String accessKey = "BrowserStack_Key";

    public static void main(String args[]) {
        try{
            userName=System.getenv("BROWSERSTACK_USERNAME");
            accessKey=System.getenv("BROWSERSTACK_KEY");
            if(userName.length()<1 || accessKey.length() <1 || userName.contains("BrowserStack") || accessKey.contains("BrowserStack")){
                System.out.println("Invalid Username or Access Key");
                System.exit(0);
            }

        }catch (Exception e){
            System.out.println("Invalid Username or Access Key");
            System.exit(0);
        }

        try {
            //API Endpoint to get a list of all browsers on BrowserStack
            URL url = new URL("https://api.browserstack.com/automate/browsers.json");
            String authStr = userName + ":" + accessKey;
            // encode data on your side using BASE64
            byte[] bytesEncoded = Base64.encodeBase64(authStr.getBytes());
            String authEncoded = new String(bytesEncoded);
            //HttpConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + authEncoded);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            //Check if the response is valid
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            } else if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output, jsonString = "";
                while ((output = br.readLine()) != null) {
                    jsonString = output;
                }
                JSONArray jsonArray = new JSONArray(jsonString);
                //Initialize variables for capturing highest version of chrome and firefox on different OS'es
                double chromeOSX = 0.0, chromeWin = 0.0, FirefoxOSX = 0.0, FirefoxWin = 0.0;
                if (jsonArray.length() > 1) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = new JSONObject(jsonArray.get(i).toString());
                        try {
                            if (jObj.get("os").toString().contains("OS X") && jObj.get("browser").toString().contains("chrome")) {
                                if (Double.parseDouble(jObj.get("browser_version").toString()) > chromeOSX) {
                                    chromeOSX = Double.parseDouble(jObj.get("browser_version").toString());
                                }
                            } else if (jObj.get("os").toString().contains("OS X") && jObj.get("browser").toString().contains("firefox")) {
                                if (Double.parseDouble(jObj.get("browser_version").toString()) > FirefoxOSX) {
                                    FirefoxOSX = Double.parseDouble(jObj.get("browser_version").toString());
                                }
                            } else if (jObj.get("os").toString().contains("Windows") && jObj.get("browser").toString().contains("chrome")) {
                                if (Double.parseDouble(jObj.get("browser_version").toString()) > chromeWin) {
                                    chromeWin = Double.parseDouble(jObj.get("browser_version").toString());
                                }
                            } else if (jObj.get("os").toString().contains("Windows") && jObj.get("browser").toString().contains("firefox")) {
                                if (Double.parseDouble(jObj.get("browser_version").toString()) > FirefoxWin) {
                                    FirefoxWin = Double.parseDouble(jObj.get("browser_version").toString());
                                }
                            }
                        } catch (Exception jsonParseException) {
                            //Print any beta/dev browsers returned by the API
                            System.out.println(jObj.get("browser") + " - " + jObj.get("browser_version") + " also available on " + jObj.get("os") + " - " + jObj.get("os_version"));
                        }
                    }
                }
                System.out.println("Latest Stable Version of Chrome on OS X: " + chromeOSX);
                System.out.println("Latest Stable Version of Firefox on OS X: " + FirefoxOSX);
                System.out.println("Latest Stable Version of Chrome on Windows: " + chromeWin);
                System.out.println("Latest Stable Version of Firefox on Windows: " + FirefoxWin);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}