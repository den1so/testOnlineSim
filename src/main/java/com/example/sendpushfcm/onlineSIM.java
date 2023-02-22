package com.example.sendpushfcm;


import com.sun.deploy.net.HttpResponse;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class onlineSIM {


    private String tzid = "0";
    private String userBalance;
    private static String ONLINESIM_SERVER_KEY = "868dde2e88601919b9aebe4424148858";

    public String getTzid() {
        return tzid;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void getBalance() throws IOException {
        String ONLINESIM_SERVER_KEY = "868dde2e88601919b9aebe4424148858";
        String ONLINESIM_ENDPOINT = "http://onlinesim.ru/api/getBalance.php?apikey=" + ONLINESIM_SERVER_KEY;


        URL url = new URL(ONLINESIM_ENDPOINT);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoOutput(true);
        con.setDoInput(true);

        con.setRequestMethod("POST");
       // con.setRequestProperty("Authorization", "apikey=" + ONLINESIM_SERVER_KEY);
        //con.setRequestProperty("Authorization", "service=" + ONLINESIM_SERVER_KEY);
        con.setRequestProperty("Content-Type", "application/json");

//        String input = "{\"to\":\"" + deviceToken +
//                "\",\"notification\":{\"title\":\"" + title +
//                "\",\"body\":\"" + body +
//                "\"}}";

//        OutputStream os = con.getOutputStream();
//        os.write(input.getBytes());
//        os.flush();
//        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("onlinaSIM response code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        userBalance = getParamJSON(String.valueOf(response),"balance");
        //System.out.println("Баланс: " + userBalance);
       // System.out.println(response.toString());
        System.out.println("Запросили баланс.");

    }

    public void getNumber(String service) throws IOException {

        String ONLINESIM_ENDPOINT = "http://onlinesim.ru/api/getNum.php";
        if(1==1) {
            URL url = new URL(ONLINESIM_ENDPOINT);
            Map<String, String> params = new HashMap<>();
            params.put("apikey", ONLINESIM_SERVER_KEY);
            params.put("service", service);
            params.put("form", "1");

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(param.getKey());
                postData.append('=');
                postData.append(param.getValue());
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                System.out.println("Читаю ответ:");
                String inputLine = in.readLine();
                tzid = getParamJSON(inputLine, "tzid");
                System.out.println("Id задания смс: " + tzid);
            } else {
                System.out.println("Request failed with response code: " + responseCode);
            }

        }


    }

    public void getState(String tz_id) throws IOException{

        String ONLINESIM_ENDPOINT = "http://onlinesim.ru/api/getState.php";
        URL url = new URL(ONLINESIM_ENDPOINT);
        Map<String, String> params = new HashMap<>();
        params.put("apikey", ONLINESIM_SERVER_KEY);
        params.put("tzid", tz_id);
        params.put("message_to_code", "0");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(param.getKey());
            postData.append('=');
            postData.append(param.getValue());
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("Читаю ответ:");
            String inputLine = in.readLine();


            String resp = getParamJSON(inputLine, "response");
            //System.out.println("Response" + resp);
            if(resp.equals("TZ_NUM_WAIT") || resp.equals("TZ_NUM_ANSWER")) {

                String number = getParamJSON(inputLine, "number");
                String time = getParamJSON(inputLine, "time");
                String msg = getParamJSON(inputLine, "msg");
                System.out.println("Номер телефона:" + number +
                        "\nВремя в секундах: " + time +
                        "\nСМС: " + msg);
            }
            else{
                System.out.println("Response: " + resp);
            }


//ответ GET getState
//[
//        {
//            "country": 7,
//                "sum": 3,
//                "service": "fastfriend",
//                "number": "+79093129065",
//                "response": "TZ_NUM_WAIT",
//                "tzid": 83301928,
//                "time": 750,
//                "form": "index"
//        }
//]
        } else {
            System.out.println("Request failed with response code: " + responseCode);
        }
    }

    public void getNumberStats() throws IOException {
        String ONLINESIM_ENDPOINT = "http://onlinesim.ru/api/getNumbersStats.php";
        URL url = new URL(ONLINESIM_ENDPOINT);
        Map<String, String> params = new HashMap<>();
        params.put("apikey", ONLINESIM_SERVER_KEY);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(param.getKey());
            postData.append('=');
            postData.append(param.getValue());
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("Читаю ответ:");
            String inputLine = in.readLine();

            String resp = getParamJSON(inputLine);

            System.out.println("getNumberStats: " + resp);
        } else {
            System.out.println("Request failed with response code: " + responseCode);
        }
    }

    public String getParamJSON(String jsonStr,String prm){
        jsonStr = jsonStr.replace(String.valueOf('['),"");
        jsonStr = jsonStr.replace(String.valueOf(']'),"");
        JSONObject json = new JSONObject(jsonStr); //jsonStr - мой json в видео строки
        String answer;
        try {
            answer = String.valueOf(json.get(prm));
        }
        catch(JSONException e){
            answer = "null";
        }
        return answer;
    }

    public String getParamJSON(String jsonStr){
       // jsonStr = jsonStr.replace(String.valueOf('['),"");
       // jsonStr = jsonStr.replace(String.valueOf(']'),"");
        JSONObject json = new JSONObject(jsonStr); //jsonStr - мой json в видео строки


        return json.toString();

        //return json.toString();
    }

    //Добавил новый комментарий

}

