package com.example.sendpushfcm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FCM {
    public void sendPushNotification(String deviceToken, String title, String body) throws Exception {
        String FCM_SERVER_KEY = "AAAAr0HcZ-w:APA91bHn_wyu-ypXpI914FlpgF5BkRXLe-3WIfN1wjh0KfP_gXQ5UyeeA1WQ2CkqrNJLdnPZ4uZzcKk9KFLmqssnJYvviYKbm0GFNE0Nzp0RRYZ9CdRKoyhpILzLGBgA-Yw2ihRP1XsR";
        String FCM_ENDPOINT = "https://fcm.googleapis.com/fcm/send";

        URL url = new URL(FCM_ENDPOINT);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoOutput(true);
        con.setDoInput(true);

        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "key=" + FCM_SERVER_KEY);
        con.setRequestProperty("Content-Type", "application/json");

        String input = "{\"to\":\"" + deviceToken +
                   "\",\"notification\":{\"title\":\"" + title +
                   "\",\"body\":\"" + body +
                  "\"}}";

        OutputStream os = con.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("FCM response code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
    }
}

