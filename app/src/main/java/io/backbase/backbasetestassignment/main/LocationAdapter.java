package io.backbase.backbasetestassignment.main;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import io.backbase.backbasetestassignment.R;
import io.backbase.backbasetestassignment.databinding.LocationItemBinding;
import io.backbase.backbasetestassignment.main.model.BookmarkedPlace;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> implements LocationItemViewModel.LocationAdapterCallback {
    public interface LocationAdapterCallback {
        void onDeleteItemClicked(BookmarkedPlace place);

        void onPlaceClicked(BookmarkedPlace place);
    }

    @NonNull private final List<BookmarkedPlace> placesList;
    @NonNull private final LocationAdapterCallback callback;

    public LocationAdapter(@NonNull List<BookmarkedPlace> placesList, @NonNull LocationAdapterCallback callback) {
        this.placesList = placesList;
        this.callback = callback;
    }

    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LocationItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.location_item, parent, false);

        return new LocationViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(LocationAdapter.LocationViewHolder holder, int position) {
        holder.itemBinding.setViewModel(new LocationItemViewModel(placesList.get(position), this));
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public void addPlace(BookmarkedPlace place) {
        placesList.add(place);
        notifyDataSetChanged();
    }

    @Override
    public void onDeleteClicked(BookmarkedPlace place) {
        placesList.remove(place);
        notifyDataSetChanged();
        callback.onDeleteItemClicked(place);
    }

    @Override
    public void onClick(BookmarkedPlace place) {
        callback.onPlaceClicked(place);
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        private LocationItemBinding itemBinding;

        LocationViewHolder(LocationItemBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;

        }
    }
}
