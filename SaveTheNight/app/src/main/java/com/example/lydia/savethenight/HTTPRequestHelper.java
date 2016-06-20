package com.example.lydia.savethenight;
/*
 * Created by Lydia on 3-6-2016.
 * HTTPRequestHelper.java
 * Called from NewsAsyncTask
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
Class that executes request with news URL
Handles return code
Returns String with result
 */
public class HTTPRequestHelper {
    // News URL
    private static final String newsURL = "http://feeds.nos.nl/nosjournaal";

    /*
    Make connection to server, execute result and handle response
    Return String with result from request
     */
    protected static synchronized String serverDownload(String... values) {
        // Declare return String result
        String result = "";

        // Convert String to url
        URL url = null;
        try {
            url = new URL(newsURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Make connection
        HttpURLConnection connection = null;
        if (url != null) {
            try {
                connection = (HttpURLConnection) url.openConnection();

                // Open connection
                connection.setRequestMethod("GET");

                // Get response code
                Integer responseCode = connection.getResponseCode();

                // If 200-299 (ok) read InputStream
                if (200 <= responseCode && responseCode <= 299) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String currentLine;
                    while ((currentLine = bufferedReader.readLine()) != null) {
                        result = result + currentLine;
                    }
                }

                // Read error stream
                else {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    if (responseCode >= 300 && responseCode < 400) {
                        result = "ERROR: redirect error:\n";
                    }
                    if (responseCode >= 400 && responseCode < 500) {
                        result = "ERROR: client error:\n";
                    }
                    if (responseCode >= 500) {
                        result = "ERROR: server error:\n";
                    }
                    // Fill result String
                    String currentLine;
                    while ((currentLine = bufferedReader.readLine()) != null) {
                        result = result + currentLine;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
