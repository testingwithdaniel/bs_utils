package utilities;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.IOException;

public class publicURLGenAutomate {
    public static String accessKey;
    public static String userName;
    public static String sessionID;
    public static  String sessionType;

    public static void main(String args[]) throws IOException, UnirestException {
        try{
            sessionID = args[0];
            userName = args[1];
            accessKey = args[2];
            sessionType=args[3];
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("One or more parameters missing: sessionID, username, key or Session Type");
            System.exit(0);
        }
        String authStr = userName+":"+accessKey;
        // encode data on your side using BASE64
        byte[] bytesEncoded = Base64.encodeBase64(authStr .getBytes());
        String authEncoded = new String(bytesEncoded);
        if(sessionType.equalsIgnoreCase("Automate")) {
            HttpResponse<String> response = Unirest.get("https://api.browserstack.com/automate/sessions/" + args[0] + ".json")
                    .header("authorization", "Basic " + authEncoded)
                    .header("cache-control", "no-cache")
                    .asString();
            JSONObject json = new JSONObject(response.getBody());
            try {
                JSONObject session = json.getJSONObject("automation_session");
                System.out.println(session.get("public_url"));
            }catch (Exception e){
                System.out.println("=============================================");
                System.out.println("==========Incorrect Details==================");
                System.out.println("=============================================");
            }
        }
        else if(sessionType.equalsIgnoreCase("App-Automate")){
            HttpResponse<String> response = Unirest.get("https://api.browserstack.com/app-automate/sessions/" + args[0] + ".json")
                    .header("authorization", "Basic " + authEncoded)
                    .header("cache-control", "no-cache")
                    .asString();
            JSONObject json = new JSONObject(response.getBody());
            JSONObject session = json.getJSONObject("automation_session");
            System.out.println(session.get("public_url"));
        }
    }
}