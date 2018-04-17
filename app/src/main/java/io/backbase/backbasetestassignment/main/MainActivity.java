package io.backbase.backbasetestassignment.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import io.backbase.backbasetestassignment.R;
import io.backbase.backbasetestassignment.common.BaseFragmentActivity;
import io.backbase.backbasetestassignment.databinding.ActivityMainBinding;
import io.backbase.backbasetestassignment.main.fragments.CityFragment;
import io.backbase.backbasetestassignment.main.fragments.HelpFragment;
import io.backbase.backbasetestassignment.main.fragments.HomeFragment;

public class MainActivity extends BaseFragmentActivity {
    @NonNull private ActivityMainBinding binding;
    @NonNull private MainActivityViewModel viewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(new HomeFragment(), false, binding.container.getId());
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(new CityFragment(), false, binding.container.getId());
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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
