package io.backbase.backbasetestassignment.main.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.backbase.backbasetestassignment.R;
import io.backbase.backbasetestassignment.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements View.OnClickListener {
    public interface HomeFragmentCallback {
        void onAddNewPlaceClicked();
    }

    @NonNull FragmentHomeBinding binding;
    @Nullable HomeFragmentCallback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.newplace.setOnClickListener(this);
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
}
