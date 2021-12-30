package com.example.project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class InventoryDatabase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "inventory.db";

    private static InventoryDatabase inventoryDB;

    //makes singleton
    public static InventoryDatabase getInstance(Context context) {
        if (inventoryDB == null) {
            inventoryDB = new InventoryDatabase(context);
        }
        return inventoryDB;
    }

    //constructor that is needed to make singleton
    private InventoryDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //this is an inner class
    //purpose is to create a specific table with specific column names
    private static final class ItemTable {
        private static final String TABLE = "items";
        private static final String ITEM_NAME_COL = "itemName";
        private static final String ITEM_ID_COL = "iDNum";
        private static final String ITEM_QUANTITY_COL = "qty";
    }

    //table for usernames and passwords
    private static final class UserTable {
        private static final String TABLE = "users";
        private static final String USER_NAME_COL = "userName";
        private static final String PASSWORD_COL = "password";
    }

    //this is to create the table within the database for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create item table
        db.execSQL("create table if not exists " + ItemTable.TABLE + " (" + ItemTable.ITEM_NAME_COL +
                " text, " + ItemTable.ITEM_ID_COL + " int primary key, " + ItemTable.ITEM_QUANTITY_COL + " int)");

        //create user table
        db.execSQL("create table if not exists " + UserTable.TABLE + " (" + UserTable.USER_NAME_COL + " text primary key, "
                + UserTable.PASSWORD_COL + " text)");
    }

    //this will re-create tables if an upgrade is needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + ItemTable.TABLE);
        db.execSQL("drop table if exists " + UserTable.TABLE);
        onCreate(db);
    }

    //getter will pull all items from the table in the database
    //no parameter is needed since this just returns the whole table
    public List<Item> getItems() {
        List<Item> itemsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        //this query tells the database what I want to get back
        String sql = "select * from " + ItemTable.TABLE;

        //cursor moves over results of query
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(0);
                int itemID = cursor.getInt(1);
                int itemQty = cursor.getInt(2);
                Item item = new Item(itemName, itemID, itemQty);
                //add new item object to the list
                itemsList.add(item);

            } while (cursor.moveToNext()); //moveToNext moves the cursor to the next result while there is a next result and returns a boolean
        }
        cursor.close();

        return itemsList;
    }


    //this inserts an item into the table and confirms the addition by returning a boolean
    public boolean addItem(Item itemToAdd) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //query, selects all user objects with usernames that match the username passed in
        String sql = "select * from " + ItemTable.TABLE + " where " + ItemTable.ITEM_ID_COL + " = ? ";

        //cursor that is based on the query results
        Cursor cursor = db.rawQuery(sql, new String[]{Integer.toString(itemToAdd.getItemID())});

        //check first if the item ID already exists in the table
        if(cursor.moveToFirst()) {
            //Item ID already exists
            return false;
        } else {
            //create "values" based on item object passed in
            values.put(ItemTable.ITEM_NAME_COL, itemToAdd.getItemName());
            values.put(ItemTable.ITEM_ID_COL, itemToAdd.getItemID());
            values.put(ItemTable.ITEM_QUANTITY_COL, itemToAdd.getItemQty());

            //insert a row into the table
            long id = db.insert(ItemTable.TABLE, null, values);
            return id != -1;
        }
    }

    //add user to the table
    public boolean addUsers(String userName, String passWord) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //query, selects all user objects with usernames that match the username passed in
        String sql = "select * from " + UserTable.TABLE + " where " + UserTable.USER_NAME_COL + " = ? ";

        //cursor that is based on the query results
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
                                            //this will give the value to substitute for the query placeholder

        //cursor.moveToFirst() will return a boolean: true if there is an item, false if there is not
        if(cursor.moveToFirst()) {
            //username already taken
            return false;
        } else {
            //create "values" based on userName and passWord
            values.put(UserTable.USER_NAME_COL, userName);
            values.put(UserTable.PASSWORD_COL, passWord);

            //insert a row into the table
            long id = db.insert(UserTable.TABLE, null, values);
            return true;
        }
    }

    //check an existing user's password
    public boolean checkPassword(String userName, String passWord) {
        SQLiteDatabase db = getWritableDatabase();

        //query to get a matching user object
        String sql = "select * from " + UserTable.TABLE + " where " + UserTable.USER_NAME_COL + " = ?";

        //cursor that is based on the query results
        Cursor cursor = db.rawQuery(sql, new String[] {userName});

        //if(there is a matching user object)
        if(cursor.moveToFirst()) {
            String queryPassword = cursor.getString(1);
            if(queryPassword.equals(passWord)) {
                return true;
            } else {
                return false;
            }
        } else {
            //the user does not exist
            return false;
        }
    }

    //pass in an item object to update
    public boolean updateItem(Item itemToUpdate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //query to get an item with matching ID
        String sql = "select * from " + ItemTable.TABLE + " where " + ItemTable.ITEM_ID_COL + " = ?";

        //cursor that is based on the query results
        Cursor cursor = db.rawQuery(sql, new String[] {Integer.toString(itemToUpdate.getItemID())});

        //if(there is a matching item object)
        if(cursor.moveToFirst()) {
            //take data from the EditText fields and put into "values"
            values.put(ItemTable.ITEM_NAME_COL, itemToUpdate.getItemName());
            values.put(ItemTable.ITEM_QUANTITY_COL, itemToUpdate.getItemQty());

            //update the table with "values"
            db.update(ItemTable.TABLE, values,
                    ItemTable.ITEM_ID_COL + " = ?", new String[] { Integer.toString(itemToUpdate.getItemID())});
            return true;

        } else {
            //if this ID number does not exist
            return false;
        }
    }

    //pass in item object to be deleted
    public void deleteItem(int itemIDToDelete) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ItemTable.TABLE,
                ItemTable.ITEM_ID_COL + " = ?", new String[] {Integer.toString(itemIDToDelete)});
                    //query string              //placeholder for ID             //tell method to use int primary key in placeholder
    }
}