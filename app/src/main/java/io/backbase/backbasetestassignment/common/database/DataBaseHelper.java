package io.backbase.backbasetestassignment.common.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.backbase.backbasetestassignment.main.model.BookmarkedPlace;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bb_test_db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BookmarkedPlace.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BookmarkedPlace.TABLE_NAME);
        onCreate(db);
    }

    public void insertPlace(@NonNull BookmarkedPlace place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookmarkedPlace.COLUMN_ID, place.getId());
        values.put(BookmarkedPlace.COLUMN_NAME, place.getPlaceName());
        values.put(BookmarkedPlace.COLUMN_LATITUDE, place.getLatitude());
        values.put(BookmarkedPlace.COLUMN_LONGITUDE, place.getLongitude());
        db.insert(BookmarkedPlace.TABLE_NAME, null, values);
    }

    public List<BookmarkedPlace> getAllStoredPlaces() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BookmarkedPlace> bookmarkedPlaces = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + BookmarkedPlace.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BookmarkedPlace place = new BookmarkedPlace(cursor.getInt(cursor.getColumnIndex(BookmarkedPlace.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(BookmarkedPlace.COLUMN_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(BookmarkedPlace.COLUMN_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(BookmarkedPlace.COLUMN_LONGITUDE)));


                bookmarkedPlaces.add(place);
            } while (cursor.moveToNext());
        }

        db.close();
        return bookmarkedPlaces;

    }

    public void deleteItem(BookmarkedPlace place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BookmarkedPlace.TABLE_NAME, BookmarkedPlace.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(place.getId())});
        db.close();
    }
}
