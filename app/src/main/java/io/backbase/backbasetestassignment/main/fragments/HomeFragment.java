package io.backbase.backbasetestassignment.main.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.backbase.backbasetestassignment.R;
import io.backbase.backbasetestassignment.common.database.DataBaseHelper;
import io.backbase.backbasetestassignment.databinding.FragmentHomeBinding;
import io.backbase.backbasetestassignment.main.LocationAdapter;
import io.backbase.backbasetestassignment.main.model.BookmarkedPlace;
import io.backbase.backbasetestassignment.network.model.WeatherInfo;

public class HomeFragment extends Fragment implements View.OnClickListener, LocationAdapter.LocationAdapterCallback {

    public interface HomeFragmentCallback {
        void onAddNewPlaceClicked();

        void onPlaceSelected(BookmarkedPlace place);
    }

    @NonNull private FragmentHomeBinding binding;
    @Nullable private HomeFragmentCallback callback;
    @NonNull private DataBaseHelper dataBaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.newplace.setOnClickListener(this);
        dataBaseHelper = new DataBaseHelper(getContext());
        List<BookmarkedPlace> storedPlaces = dataBaseHelper.getAllStoredPlaces();
        binding.locationList.setAdapter(new LocationAdapter(storedPlaces, this));
        return binding.getRoot();
    }

    public void setCallback(@Nullable HomeFragmentCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        if (callback != null) {
            callback.onAddNewPlaceClicked();
        }
    }

    //I don't like that much casting but i've only 8 hours...
    public void onNewPlaceBookmarked(WeatherInfo weatherInfo) {

        //Having time all this logic was in the ViewModel and could have been tested.
        BookmarkedPlace bookmarkedPlace = new BookmarkedPlace(weatherInfo);
        dataBaseHelper.insertPlace(bookmarkedPlace);
        //Was nicer adding an observableField...
        binding.textView.setVisibility(View.GONE);
        ((LocationAdapter) binding.locationList.getAdapter()).addPlace(bookmarkedPlace);
    }

    @Override
    public void onDeleteItemClicked(BookmarkedPlace place) {
        dataBaseHelper.deleteItem(place);
    }

    @Override
    public void onPlaceClicked(BookmarkedPlace place) {
        if (callback != null) {
            callback.onPlaceSelected(place);
        }
    }
}
