package com.company;


import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;


public class PostRequest {
    public static void main(String[] args) {
        String color = null;
        int box = 1;
        String url = "http://127.0.0.1:8080/index";
        if (color != null) {
            if (url.endsWith("index")) {
                url = url + "?";
            } else {
                url = url + "&";
            }
            url = url + "color=" + color;
        }
        if (box > 0) {
            if (url.endsWith("index")) {
                url = url + "?";
            } else {
                url = url + "&";
            }
            url = url + "box=" + String.valueOf(box);
        }
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            String theString = IOUtils.toString((connection.getInputStream()), "UTF-8");
            System.out.println(theString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
