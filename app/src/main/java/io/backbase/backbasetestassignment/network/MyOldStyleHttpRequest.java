package io.backbase.backbasetestassignment.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.backbase.backbasetestassignment.BuildConfig;
import io.backbase.backbasetestassignment.network.model.WeatherInfo;

public class MyOldStyleHttpRequest extends AsyncTask<String, Void, WeatherInfo> {
    @NonNull private final String latitude;
    @NonNull private final String longitude;

    public interface MyOldStyleHttpRequestCallback {
        void onCallDone(WeatherInfo result);

        void onCallError(String errorMessage);
    }

    private static final String SCHEME_HTTP = "http";
    private static final String ENDPOINT = "api.openweathermap.org";
    private static final String PATH = "data/2.5/weather";
    private static final String METHOD_REQUEST_GET = "GET";
    private static final String METHOD_PARAM_LAT = "lat";
    private static final String METHOD_PARAM_LONG = "lon";
    private static final String METHOD_PARAM_KEY = "appid";
    private static final String METHOD_PARAM_UNIT = "units";
    private static final int TIMEOUT = 10000;

    @NonNull private final MyOldStyleHttpRequestCallback callback;

    public MyOldStyleHttpRequest(double latitude, double longitude, @NonNull MyOldStyleHttpRequestCallback callback) {
        this.callback = callback;
        this.latitude = String.valueOf(latitude);
        this.longitude = String.valueOf(longitude);
    }


    @Override
    protected WeatherInfo doInBackground(String... strings) {
        try {
            //those hardcoded strings should not be here, but should be exported.
            Uri uri = new Uri.Builder()
                    .scheme(SCHEME_HTTP)
                    .authority(ENDPOINT)
                    .path(PATH)
                    .appendQueryParameter(METHOD_PARAM_LAT, latitude)
                    .appendQueryParameter(METHOD_PARAM_LONG, longitude)
                    .appendQueryParameter(METHOD_PARAM_KEY, BuildConfig.SecApiKey)
                    .appendQueryParameter(METHOD_PARAM_UNIT, "Metric")
                    .build();
            URL todayUrl = new URL(uri.toString());
            HttpURLConnection connection = (HttpURLConnection) todayUrl.openConnection();
            connection.setRequestMethod(METHOD_REQUEST_GET);
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);

            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(stringBuilder.toString(), WeatherInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TEST", "Exception: " + e.getLocalizedMessage());
            callback.onCallError(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(WeatherInfo weatherInfo) {
        super.onPostExecute(weatherInfo);
        callback.onCallDone(weatherInfo);
    }
}
