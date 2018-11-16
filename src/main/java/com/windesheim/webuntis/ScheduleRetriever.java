package com.windesheim.webuntis;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Lucas Ouwens
 */
public class ScheduleRetriever {

    private static ScheduleRetriever instance = null;

    public static ScheduleRetriever getInstance() {
        if (instance == null) {
            instance = new ScheduleRetriever();
        }

        return instance;
    }


    private HttpURLConnection getConnection(String URI) throws IOException {
        return (HttpURLConnection) new URL(URI).openConnection();
    }

    public InputStreamReader getScheduleByClass(String webuntisBinding) throws IOException {
        HttpURLConnection connection = getConnection("http://api.windesheim.nl/api/Klas/" + webuntisBinding + "/les");
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        return new InputStreamReader(connection.getInputStream());
    }


}
