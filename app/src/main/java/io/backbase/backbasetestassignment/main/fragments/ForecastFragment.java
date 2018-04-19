package io.backbase.backbasetestassignment.main.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.backbase.backbasetestassignment.R;
import io.backbase.backbasetestassignment.databinding.FragmentForecastBinding;
import io.backbase.backbasetestassignment.main.model.BookmarkedPlace;

public class ForecastFragment extends Fragment {
    public final static String FORECAST_FRAGMENT_KEY = "FORECAST_FRAGMENT_KEY";
    private final static String TAG = ForecastFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentForecastBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast, container, false);
        if (getArguments() != null && savedInstanceState == null) {
            BookmarkedPlace selectedPlace = getArguments().getParcelable(FORECAST_FRAGMENT_KEY);
            ForecastFragmentViewModel viewModel = new ForecastFragmentViewModel();
            binding.setViewModel(viewModel);
            if (selectedPlace != null) {
                viewModel.fetchWeatherInfoForLocation(selectedPlace);
            } else {
                Log.e(TAG, "Please provide proper place in order to visualize the forecast correctly");
            }
        }
        return binding.getRoot();
    }
}
