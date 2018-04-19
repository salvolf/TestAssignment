package io.backbase.backbasetestassignment.main;

import android.support.annotation.NonNull;
import android.view.View;

import io.backbase.backbasetestassignment.main.model.BookmarkedPlace;

public class LocationItemViewModel implements View.OnClickListener {
    interface LocationAdapterCallback {
        void onDeleteClicked(BookmarkedPlace place);
        void onClick(BookmarkedPlace place);
    }

    @NonNull public final BookmarkedPlace place;
    @NonNull private final LocationAdapterCallback callback;

    public LocationItemViewModel(@NonNull BookmarkedPlace place, @NonNull LocationAdapterCallback callback) {
        this.place = place;
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        callback.onClick(place);
    }

    public void onDeleteClicked() {
        callback.onDeleteClicked(place);
    }
}
