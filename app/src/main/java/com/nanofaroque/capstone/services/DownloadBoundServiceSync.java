package com.nanofaroque.capstone.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.nanofaroque.capstone.aidl.WeatherCall;
import com.nanofaroque.capstone.aidl.WeatherData;
import com.nanofaroque.capstone.data.ResponseData;
import com.nanofaroque.capstone.utils.DownloadUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by ofaro on 5/31/2015.
 */
public class DownloadBoundServiceSync extends Service {

    WeatherCall.Stub mWeatherCallImpl=new WeatherCall.Stub() {
        @Override
        public List<WeatherData> getCurrentWeather(String Weather) throws RemoteException {
            ArrayList<WeatherData> weatherList=new ArrayList<>();
            String json=DownloadUtils.downloadData(getApplicationContext(),Weather);
            WeatherData weatherData=DownloadUtils.createObject(json);
            weatherList.add(weatherData);
            return weatherList;
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mWeatherCallImpl;
    }

    public static Intent makeIntent(Context context) {
        // Create an explicit Intent and return it to the caller.
        return new Intent(context,
                DownloadBoundServiceSync.class);
    }
}
