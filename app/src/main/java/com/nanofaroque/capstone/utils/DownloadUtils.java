package com.nanofaroque.capstone.utils;

import android.content.Context;
import android.util.Log;

import com.nanofaroque.capstone.aidl.WeatherData;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ofaro on 5/31/2015.
 */
public class DownloadUtils {

    public static String downloadData(Context context,
                                     String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        Log.d("output:",result);
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static WeatherData createObject(String json) {
        String name = null;
        String maxTemp = null;
        String humidity = null;
        String sunrise = null;
        WeatherData weatherData;
        String sunset = null;
        String speed = null;
        String deg = null;

        try {
            JSONObject mainObj=new JSONObject(json);
            JSONObject coorObj=mainObj.getJSONObject("coord");


            JSONObject sysObj=mainObj.getJSONObject("sys");
            sunrise=sysObj.getString("sunrise");
            sunset=sysObj.getString("sunset");

            JSONObject mObj=mainObj.getJSONObject("main");
            humidity=mObj.getString("humidity");
            maxTemp=mObj.getString("temp_max");
            name=mainObj.getString("name");
            speed=mainObj.getJSONObject("wind").getString("speed");
            deg=mainObj.getJSONObject("wind").getString("deg");
            Log.d("lat:",coorObj.getString("lat")+"sunrise: "+sunrise+ "Sunset:  "+sunset+
                    "Humidity:  "+humidity+"MaxTemp:  "+maxTemp+"Name:  "+name+"Speed:   "+speed+"Deg:  "+deg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new WeatherData(name,Double.parseDouble(speed),Double.parseDouble(deg),
                Double.parseDouble(maxTemp),Long.parseLong(humidity),Long.parseLong(sunrise),Long.parseLong(sunset));

    }
}
