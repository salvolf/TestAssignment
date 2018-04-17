package io.backbase.backbasetestassignment.common;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class BaseFragmentActivity extends AppCompatActivity {

    public void showFragment(@NonNull Fragment fragment, boolean addToBackStack, int containerId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment alreadyaddedFragment = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());
        if (alreadyaddedFragment != null && !alreadyaddedFragment.isVisible()) {
            transaction.replace(containerId, alreadyaddedFragment, alreadyaddedFragment.getClass().getSimpleName()).commit();
        } else {
            if (alreadyaddedFragment == null) {
                if (addToBackStack) {
                    transaction.addToBackStack(fragment.getClass().getSimpleName());
                }
                transaction.replace(containerId, fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }
}
