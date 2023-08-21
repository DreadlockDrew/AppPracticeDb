package com.example.dbpractice1.DataIntegration;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemDatabase extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "items_db";
    private static final int DATABASE_VERSION = 1;

    // defines the name of the table that will hold user information
    private static final String TABLE_ITEMS = "ITEMS";

    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_ITEMID= "itemID";
    private static final String COLUMN_ITEM_NAME = "itemName";
    private static final String COLUMN_ITEMQUANTITY = "itemQuantity";




    private static final String CREATE_TABLE_ITEMS =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USERNAME + " TEXT," +
                    COLUMN_ITEMQUANTITY + " INTEGER," +
                    COLUMN_ITEM_NAME + " TEXT" + ")";

    public void insertItem(String username, String quantity, String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_ITEMQUANTITY, Integer.parseInt(quantity)); // Convert to integer
        values.put(COLUMN_ITEM_NAME, itemName);

        try {
            db.insert(TABLE_ITEMS, null, values);
        } catch (SQLiteException e) {
            // Handle exception (e.g., log it or show a message to the user)
        } finally {
            db.close();
        }
    }

    public void deleteItem(int itemID) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TABLE_ITEMS, COLUMN_ITEMID + " = ?", new String[] { String.valueOf(itemID) });
        } catch (SQLiteException e) {
            // Handle exception (e.g., log it or show a message to the user)
        } finally {
            db.close();
        }
    }

    public void updateItem(int itemID, String itemName, int itemQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, itemName);
        values.put(COLUMN_ITEMQUANTITY, itemQuantity);

        db.update(TABLE_ITEMS, values, COLUMN_ITEMID + " = ?", new String[]{String.valueOf(itemID)});
        db.close();
    }



    public List<Item> getItemsByUsername(String username) {
        List<Item> items = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_ITEMS, null, COLUMN_USERNAME + " = ?",
                    new String[]{username}, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int itemId = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEMID));
                    @SuppressLint("Range") String itemName = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
                    @SuppressLint("Range") int itemQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEMQUANTITY));

                    Item item = new Item(itemId, itemName, itemQuantity);
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            // Handle exception (e.g., log it or show a message to the user)
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return items;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ITEMS);}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_ITEMS);
        } catch (SQLiteException e) {
            // Handle exception, e.g., log it
        } finally {
            db.close();
        }
    }
    public ItemDatabase(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

}
