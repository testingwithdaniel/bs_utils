package utilities;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class getLogs {
    public static String accessKey;
    public static String userName;
    public static String sessionID;
    public static void main(String[] args) throws IOException {
        try {
            try{
                sessionID = args[0];
                userName = args[1];
                accessKey = args[2];
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("One or more parameters missing: sessionID, username or key");
                System.out.println(args[0]);
                System.exit(1);
            }

            String session_info=getDataFromLink("https://api.browserstack.com/automate/sessions/"+sessionID+".json",false);
            JSONObject json = new JSONObject(session_info);
            JSONObject session = json.getJSONObject("automation_session");
            String logsLink =session.get("logs").toString();

            String logs = getDataFromLink(logsLink,true);
            String path = "./logs/LogsToParse.txt";
            writeToFile(logs,path);
            analyzeLogs();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getDataFromLink(String link,boolean isLog) throws IOException {
        URL url = new URL(link);
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
            System.out.println("Could not fetch data, please ensure the session ID is valid OR verify if API is functional. Also check if the username and access key are valid");
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output;
        String l="";
        if(!isLog) {
            while ((output = br.readLine()) != null) {
                l = l.concat(output);
            }
        }else if(isLog){
            while ((output = br.readLine()) != null) {
                l = l.concat(output);
                l=l.concat("\n");
            }
        }
        conn.disconnect();
        return l;
    }

    private static void writeToFile(String logs, String file) throws IOException {
        PrintWriter out = new PrintWriter(file);
        out.println(logs);
        out.close();
    }
    private static void analyzeLogs() {
        StringBuffer stringBuffer = new StringBuffer();
        int zeroToTwo=0,TwoToFive=0,sixToTen=0,TenToFifteen=0,morethan15=0;
        Multimap<String, String> multimap = ArrayListMultimap.create();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        try {
            File file = new File("./logs/LogsToParse.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(stringBuffer.length()>0){
            if(stringBuffer.toString().contains("START_SESSION")){
                Date start_Time;
                String[] lines = stringBuffer.toString().split("\\r?\\n");
                List<String> logEntriestemp = Arrays.asList(lines);
                ArrayList<String> logEntries=new ArrayList<>(logEntriestemp);
                do{
                    if(logEntries.get(0).contains("START_SESSION")){
                        String dateToParse=logEntries.get(0).substring(0,logEntries.get(0).indexOf(" START"));
                        try
                        {
                            start_Time=simpleDateFormat.parse(dateToParse);
                            simpleDateFormat.format(start_Time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    else{
                        logEntries.remove(0);
                    }
                }while(true);
                int counter=0;
                for(int i=1;i<logEntries.size();i+=2){
                    String command="";
                    Date start=null,end=null;
                    if(logEntries.get(i).contains("REQUEST [")){
                        String dateToParse=logEntries.get(i).substring(0,logEntries.get(i).indexOf("REQUEST ["));
                        if(logEntries.get(i).contains("GET /")){
                            command=logEntries.get(i).substring(logEntries.get(i).indexOf("GET /"),logEntries.get(i).length());
                        }else if(logEntries.get(i).contains("POST /")){
                            command=logEntries.get(i).substring(logEntries.get(i).indexOf("POST /"));
                        }
                        try {
                            start=simpleDateFormat.parse(dateToParse);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(logEntries.get(i+1).contains("RESPONSE {")){
                            dateToParse=logEntries.get(i+1).substring(0,logEntries.get(i+1).indexOf("RESPONSE {"));
                            try {
                                end=simpleDateFormat.parse(dateToParse);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if(logEntries.get(i+1).contains("DEBUG ")){
                            dateToParse=logEntries.get(i+2).substring(0,logEntries.get(i+2).indexOf("RESPONSE {"));
                            try {
                                end=simpleDateFormat.parse(dateToParse);
                                i++;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        long duration  = end.getTime() - start.getTime();
                        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
                        multimap.put(String.valueOf(diffInSeconds),command);
                        counter++;
                        if(diffInSeconds<=2){
                            zeroToTwo++;
                        }
                        else if(diffInSeconds>2 && diffInSeconds <=5){
                            TwoToFive++;
                        }else if(diffInSeconds>5 && diffInSeconds<=10){
                            sixToTen++;
                        }else if(diffInSeconds>10 && diffInSeconds<=15){
                            TenToFifteen++;
                        }else{
                            morethan15++;
                        }
                    }
                }
            }
            else{
                System.out.println("Unable to find Start session parameter in logs, please ensure you paste complete logs");
            }
        }else{
            System.out.println("Unable to read content from LogsToParse.txt file");
        }
        System.out.println("Commands between 0-2 seconds: "+zeroToTwo);
        System.out.println("Commands between 3-5 seconds: "+TwoToFive);
        System.out.println("Commands between 5-10 seconds: "+sixToTen);
        System.out.println("Commands between 11-15 seconds: "+TenToFifteen);
        System.out.println("Commands more than 15 seconds: "+morethan15);
        Set<String> timesTemp=multimap.keySet();
        List newTimes = new ArrayList();
        newTimes.addAll(timesTemp);
        Collections.reverse(newTimes);
        timesTemp=new HashSet<>(newTimes);
        for (String keyprint : timesTemp) {
            System.out.println("Time in Seconds = " + keyprint);
            Collection<String> values = multimap.get(keyprint);
            for(String value : values){
                System.out.println("Command= "+ value.replace("/session/"+sessionID+"/",""));
            }
        }
    }
}
