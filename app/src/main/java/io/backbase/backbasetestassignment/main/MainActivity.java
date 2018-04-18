package io.backbase.backbasetestassignment.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import io.backbase.backbasetestassignment.R;
import io.backbase.backbasetestassignment.common.BaseFragmentActivity;
import io.backbase.backbasetestassignment.databinding.ActivityMainBinding;
import io.backbase.backbasetestassignment.main.fragments.HelpFragment;
import io.backbase.backbasetestassignment.main.fragments.HomeFragment;

public class MainActivity extends BaseFragmentActivity implements HomeFragment.HomeFragmentCallback {
    private static final int PLACE_PICKER_REQUEST = 1010;
    public static final String TAG = MainActivity.class.getSimpleName();


    @NonNull private ActivityMainBinding binding;
    @NonNull private MainActivityViewModel viewModel;
    @Nullable private Intent placeIntent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showHomeFragment();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
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
        if (savedInstanceState == null) {
            viewModel = new MainActivityViewModel();
            binding.setViewModel(viewModel);
        }
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        showHomeFragment();
    }

    private void showHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setCallback(this);
        showFragment(homeFragment, false, binding.container.getId());

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
