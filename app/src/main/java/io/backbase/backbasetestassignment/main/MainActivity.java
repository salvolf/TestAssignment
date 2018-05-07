package io.backbase.backbasetestassignment.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import io.backbase.backbasetestassignment.R;
import io.backbase.backbasetestassignment.common.BaseFragmentActivity;
import io.backbase.backbasetestassignment.databinding.ActivityMainBinding;
import io.backbase.backbasetestassignment.main.fragments.ForecastFragment;
import io.backbase.backbasetestassignment.main.fragments.HelpFragment;
import io.backbase.backbasetestassignment.main.fragments.HomeFragment;
import io.backbase.backbasetestassignment.main.model.BookmarkedPlace;
import io.backbase.backbasetestassignment.network.ApiManager;
import io.backbase.backbasetestassignment.network.impl.ApiManagerImpl;
import io.backbase.backbasetestassignment.network.model.WeatherInfo;

public class MainActivity extends BaseFragmentActivity implements HomeFragment.HomeFragmentCallback, ApiManager.ApiStatusCallback {
    private static final int PLACE_PICKER_REQUEST = 1010;
    private static final String TAG = MainActivity.class.getSimpleName();


    @NonNull private ActivityMainBinding binding;
    @NonNull private MainActivityViewModel viewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    binding.navigation.setVisibility(View.VISIBLE);
                    showHomeFragment();
                    return true;
                case R.id.navigation_notifications:
                    binding.navigation.setVisibility(View.VISIBLE);
                    showFragment(new HelpFragment(), false, binding.container.getId());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainActivityViewModel();
        binding.setViewModel(viewModel);
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showHomeFragment();
    }

    private void showHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        HomeFragment addedFragment = (HomeFragment) showFragment(homeFragment, false, binding.container.getId());
        if (addedFragment != null) {
            addedFragment.setCallback(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onAddNewPlaceClicked() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            Log.e(TAG, "GooglePlayServicesNotAvailableException");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment shownfragment = getSupportFragmentManager().findFragmentById(binding.container.getId());
        binding.navigation.setVisibility(shownfragment instanceof ForecastFragment ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onPlaceSelected(BookmarkedPlace place) {
        ForecastFragment forecastFragment = new ForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ForecastFragment.FORECAST_FRAGMENT_KEY, place);
        forecastFragment.setArguments(bundle);
        binding.navigation.setVisibility(View.GONE);
        showFragment(forecastFragment, true, binding.container.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                ApiManager apiManager = new ApiManagerImpl(this);
                apiManager.getTodayForecastForLocation(place.getLatLng().latitude, place.getLatLng().longitude);
            }
        }
    }


    @Override
    public void onCallCompleted(WeatherInfo response) {
        HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        if (fragment != null) {
            fragment.onNewPlaceBookmarked(response);
        }

    }

    @Override
    public void onCallFailed(String errorMessage) {
        //TODO notify view to show error
    }
}
