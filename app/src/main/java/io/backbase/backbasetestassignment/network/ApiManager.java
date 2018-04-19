package io.backbase.backbasetestassignment.network;

import io.backbase.backbasetestassignment.network.model.WeatherInfo;

public interface ApiManager {
     interface ApiStatusCallback {
        void onCallCompleted(WeatherInfo response);

        void onCallFailed(String errorMessage);
    }

    void getTodayForecastForLocation(double latitude, double longitude);
}
