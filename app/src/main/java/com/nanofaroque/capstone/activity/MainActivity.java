package com.nanofaroque.capstone.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nanofaroque.capstone.R;
import com.nanofaroque.capstone.aidl.WeatherCall;
import com.nanofaroque.capstone.aidl.WeatherData;
import com.nanofaroque.capstone.aidl.WeatherRequest;
import com.nanofaroque.capstone.aidl.WeatherResults;
import com.nanofaroque.capstone.services.DownloadBoundServiceAsync;
import com.nanofaroque.capstone.services.DownloadBoundServiceSync;

import java.io.Serializable;
import java.util.List;


public class MainActivity extends Activity {

    public final String TAG=this.getClass().getSimpleName();
    private EditText etCityState;
    String cityStateName;
    WeatherData mWeatherData;
    //aidl for two ways communication and sync communication
    WeatherCall mWeatherCall;

    //aidl for one way communication and async service
    WeatherRequest mWeatherRequest;


    ServiceConnection mServiceConnSync=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"Component Name:"+name);
            mWeatherCall=WeatherCall.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mWeatherCall=null;

        }
    };


    ServiceConnection mServiceConnAsync=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"Component Name:"+name);
            mWeatherRequest=WeatherRequest.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mWeatherRequest=null;

        }
    };


    WeatherResults.Stub mWeatherResults=new WeatherResults.Stub() {
        @Override
        public void sendResults(List<WeatherData> results) throws RemoteException {
            Log.d("RESULT Async:",results.toString());
            Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
            //intent.putParcelableArrayListExtra("data",results);
            intent.putExtra("data", results.get(0));
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCityState=(EditText)findViewById(R.id.etCityState);


    }


    public void runService(View view) {
        cityStateName=etCityState.getText().toString();
        String url="http://api.openweathermap.org/data/2.5/weather?q="+cityStateName;
       // Uri uri=Uri.parse(url);

        hideKeyboard();

        switch(view.getId()) {
            case R.id.bound_sync_button:
                if (mWeatherCall != null) {
                    Log.d(TAG,
                            "Calling twoway DownloadServiceSync.downloadImage()");
                    /**
                     * Define an AsyncTask instance to avoid blocking the UI Thread.
                     * */
                    new AsyncTask<String, Void, List<WeatherData>>() {
                        @Override
                        protected List<WeatherData> doInBackground(String... params) {
                            try {
                                Log.d("params:",params[0]);
                                //DownloadUtils.downloadData(MainActivity.this,params[0]);
                                return mWeatherCall.getCurrentWeather(params[0].toString());
                            } catch(RemoteException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                        /**
                         * Runs in a background thread.
                         */


                        /**
                         * Runs in the UI Thread.
                         */
                        @Override
                        protected void onPostExecute(List<WeatherData> weatherData) {
                            super.onPostExecute(weatherData);
                            Log.d("RESULT:",weatherData.toString());
                            Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
                            mWeatherData=weatherData.get(0);
                            intent.putExtra("data", weatherData.get(0));
                            startActivity(intent);
                            //updateUi();

                        }
                    }.execute(url);
                }
                break;

            case R.id.bound_async_button:
                if(mWeatherRequest != null) {
                    try {
                        Log.d(TAG,
                                "Calling oneway DownloadServiceAsync.downloadImage()");

                        // Call downloadImage() on mDownloadRequest, passing in
                        // the appropriate Uri and Results.
                        mWeatherRequest.getCurrentWeather(url,
                                mWeatherResults);
                    } catch(RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }



    private void hideKeyboard() {
        InputMethodManager mgr =
                (InputMethodManager) getSystemService
                        (Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(etCityState.getWindowToken(),
                0);
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(mWeatherCall == null)
            bindService(DownloadBoundServiceSync.makeIntent(this),
                    mServiceConnSync,
                    BIND_AUTO_CREATE);
        if(mWeatherRequest == null)
            bindService(DownloadBoundServiceAsync.makeIntent(this),
                    mServiceConnAsync,
                    BIND_AUTO_CREATE);

    }



    @Override
    protected void onStop() {
        super.onStop();
        /*if(mWeatherCall != null)
            unbindService(mServiceConnSync);
        if(mWeatherRequest != null)
            unbindService(mServiceConnAsync);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mWeatherCall != null)
            unbindService(mServiceConnSync);
        if(mWeatherRequest != null)
            unbindService(mServiceConnAsync);
    }
}
