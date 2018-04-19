package io.backbase.backbasetestassignment.main.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import io.backbase.backbasetestassignment.network.model.WeatherInfo;

public class BookmarkedPlace implements Parcelable {

    public static final String TABLE_NAME = "places";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "placename";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_LATITUDE + " REAL,"
                    + COLUMN_LONGITUDE + " REAL"
                    + ")";

    @NonNull private Integer id;
    @NonNull private String placeName;
    private double latitude;
    private double longitude;

    public BookmarkedPlace(WeatherInfo weatherInfo) {
        this.id = weatherInfo.getId();
        this.placeName = weatherInfo.getName();
        this.latitude = weatherInfo.getCoord().getLat();
        this.longitude = weatherInfo.getCoord().getLon();
    }

    public BookmarkedPlace(int id, @NonNull String placeName, double latitude, double longitude) {
        this.id = id;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private BookmarkedPlace(Parcel in) {
        id = in.readInt();
        placeName = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<BookmarkedPlace> CREATOR = new Creator<BookmarkedPlace>() {
        @Override
        public BookmarkedPlace createFromParcel(Parcel in) {
            return new BookmarkedPlace(in);
        }

        @Override
        public BookmarkedPlace[] newArray(int size) {
            return new BookmarkedPlace[size];
        }
    };

    public int getId() {
        return id;
    }

    @NonNull
    public String getPlaceName() {
        return placeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) 1);
        dest.writeInt(id);
        dest.writeString(placeName);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
