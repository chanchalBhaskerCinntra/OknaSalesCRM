package com.cinntra.okana.webservices;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by displ22 on 6/9/2016.
 */
public class Post_WebService  {
    String JsonResponse = null;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    public String connect_service(String Url,String param) {
        String JsonDATA = param;
        try {
            URL url = new URL(Url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter

            urlConnection.setConnectTimeout(25000);
            urlConnection.setReadTimeout(22000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("request_json", JsonDATA.toString());
           // urlConnection.setDoOutput(false);
            urlConnection.setRequestProperty("Content-Type", "image/jpeg");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            //for Image
            urlConnection.setUseCaches(false);
            urlConnection.setAllowUserInteraction(true);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setRequestProperty("Accept", "*/*");

            //set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            //  writer.append("request_json");
            Log.e("Writable Data=>", JsonDATA.toString());
            writer.write(JsonDATA);
            // json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
            //input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            JsonResponse = buffer.toString();
            //response data
            Log.i("TAG Test", JsonResponse);
            try {
//send to post execute
                return JsonResponse;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();

            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("TAG", "Error closing stream", e);
                }
            }
           }
            return JsonResponse;
        }

}
