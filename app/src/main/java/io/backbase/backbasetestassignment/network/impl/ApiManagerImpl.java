package io.backbase.backbasetestassignment.network.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import io.backbase.backbasetestassignment.network.ApiManager;
import io.backbase.backbasetestassignment.network.MyOldStyleHttpRequest;
import io.backbase.backbasetestassignment.network.model.WeatherInfo;

public class ApiManagerImpl implements ApiManager {
    private static final String TAG = ApiManagerImpl.class.getSimpleName();
    @NonNull private final ApiStatusCallback callback;

    public ApiManagerImpl(@NonNull ApiStatusCallback callback) {
        this.callback = callback;
    }

    public void getTodayForecastForLocation(double latitude, double longitude) {
        new MyOldStyleHttpRequest(latitude, longitude,
                new MyOldStyleHttpRequest.MyOldStyleHttpRequestCallback() {
                    @Override
                    public void onCallDone(WeatherInfo result) {
                        callback.onCallCompleted(result);
                    }

                    @Override
                    public void onCallError(String errorMessage) {
                        Log.e(TAG, "Error: " + errorMessage);
                        callback.onCallFailed(errorMessage);
                    }
                }).execute();
    }
}



