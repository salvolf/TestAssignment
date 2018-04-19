package io.backbase.backbasetestassignment.main.fragments;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import io.backbase.backbasetestassignment.R;
import io.backbase.backbasetestassignment.main.model.BookmarkedPlace;
import io.backbase.backbasetestassignment.network.MyOldStyleHttpRequest;
import io.backbase.backbasetestassignment.network.model.WeatherInfo;

public class ForecastFragmentViewModel {
    private final static String TAG = ForecastFragmentViewModel.class.getSimpleName();

    //Need those public for DataBinding
    @NonNull public ObservableInt iconId = new ObservableInt();
    @NonNull public ObservableInt progressBarVisibility = new ObservableInt(View.VISIBLE);
    @NonNull public ObservableField<String> desc = new ObservableField<>("");
    @NonNull public ObservableField<String> temp = new ObservableField<>("");
    @NonNull public ObservableField<String> tempMin = new ObservableField<>("");
    @NonNull public ObservableField<String> tempMax = new ObservableField<>("");
    @NonNull public ObservableField<String> windSpeed = new ObservableField<>("");
    @NonNull public ObservableField<String> windDirection = new ObservableField<>("");

    public ForecastFragmentViewModel() {

    }

    public void fetchWeatherInfoForLocation(@NonNull BookmarkedPlace selectedPlace) {
        new MyOldStyleHttpRequest(selectedPlace.getLatitude(), selectedPlace.getLongitude(), new MyOldStyleHttpRequest.MyOldStyleHttpRequestCallback() {
            @Override
            public void onCallDone(WeatherInfo result) {
                progressBarVisibility.set(View.GONE);
                updateView(result);
            }

            private void updateView(WeatherInfo result) {
                desc.set(result.getWeather().get(0).getDescription());
                temp.set(String.valueOf(result.getMain().getTemp().intValue()));
                tempMin.set(String.valueOf(result.getMain().getTempMax().intValue()));
                tempMax.set(String.valueOf(result.getMain().getTempMin().intValue()));
                windSpeed.set(String.valueOf(result.getWind().getSpeed()));
                windDirection.set(String.valueOf(result.getWind().getDeg()));
                iconId.set(R.drawable.sun);

            }

            @Override
            public void onCallError(String errorMessage) {
                //TODO Error handling!
                Log.e(TAG, "Error on api call: " + errorMessage);
                progressBarVisibility.set(View.GONE);

            }
        }).execute();
    }
}
