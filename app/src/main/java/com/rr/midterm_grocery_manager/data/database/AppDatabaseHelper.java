package com.rr.midterm_grocery_manager.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rr.midterm_grocery_manager.GroceryItem;

import java.util.ArrayList;
import java.util.List;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "grocery.db";
    private static final int DATABASE_VERSION = 1;

    // Grocery table
    private static final String TABLE_GROCERY = "grocery";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CATEGORY = "category";

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + TABLE_GROCERY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_CATEGORY + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_GROCERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERY);
        onCreate(db);
    }


    public void addGroceryItem(GroceryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, item.getName());
            values.put(COLUMN_CATEGORY, item.getCategory());
            db.insert(TABLE_GROCERY, null, values);
        } finally {
            db.close();
        }
    }


    public List<GroceryItem> getAllGroceryItems() {
        List<GroceryItem> groceryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_GROCERY, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_CATEGORY},
                    null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    GroceryItem item = new GroceryItem();
                    item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    item.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                    item.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                    groceryList.add(item);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return groceryList;
    }


    public void deleteGroceryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_GROCERY, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        } finally {
            db.close();
        }
    }
}
