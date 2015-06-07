package com.nanofaroque.capstone.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.nanofaroque.capstone.aidl.WeatherData;
import com.nanofaroque.capstone.aidl.WeatherRequest;
import com.nanofaroque.capstone.aidl.WeatherResults;
import com.nanofaroque.capstone.data.ResponseData;
import com.nanofaroque.capstone.utils.DownloadUtils;

import java.util.ArrayList;

/**
 * Created by ofaro on 5/31/2015.
 */
public class DownloadBoundServiceAsync extends Service {

    WeatherRequest.Stub mWeatherRequestImpl=new WeatherRequest.Stub() {
        @Override
        public void getCurrentWeather(String Weather, WeatherResults results) throws RemoteException {
            ArrayList<WeatherData> weatherList=new ArrayList<>();
            String json= DownloadUtils.downloadData(getApplicationContext(), Weather);
            WeatherData weatherData=DownloadUtils.createObject(json);

            weatherList.add(weatherData);
            results.sendResults(weatherList);
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mWeatherRequestImpl;
    }

    public static Intent makeIntent(Context context) {
        // Create an explicit Intent and return it to the caller.
        return new Intent(context,
                DownloadBoundServiceAsync.class);
    }
}
