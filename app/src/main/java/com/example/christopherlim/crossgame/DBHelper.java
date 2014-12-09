package com.example.christopherlim.crossgame;

/**
 * Created by Grace on 11/23/2014.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_DEVICEID = "deviceid";
    public static final String CONTACTS_COLUMN_LASTNAME = "lastname";
    public static final String CONTACTS_COLUMN_FIRSTNAME = "firstname";
    public static final String CONTACTS_COLUMN_AGE = "age";
    public static final String CONTACTS_COLUMN_GENDER = "gender";
    public static final String CONTACTS_COLUMN_ORIENTATION = "orientation";
    public static final String CONTACTS_COLUMN_PHONENUMBER = "phonenumber";
    public static final String CONTACTS_COLUMN_TAGLINE = "tagline";
    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COLUMN_DEVICEID = "deviceid";
    public static final String USERS_COLUMN_NAME = "name";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String CREATE_CONTACTS_TABLE= "CREATE TABLE contacts ( " +
                "id INTEGER PRIMARY KEY, " +
                "lastname TEXT, " +
                "firstname TEXT, " +
                "age TEXT, " +
                "gender TEXT, " +
                "orientation TEXT, " +
                "phonenumber TEXT, " +
                "tagline TEXT, " +
                "deviceid TEXT )"
                ;

        String CREATE_USERS_TABLE= "CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY, " +
                "deviceid TEXT, " +
                "name TEXT )"
                ;

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean insertContact  (String lastname, String firstname, String age, String gender, String orientation, String phonenumber, String tagline, String deviceid, int mode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if(mode == 0) {
            contentValues.put("lastname", lastname);
            contentValues.put("firstname", firstname);
            contentValues.put("age", age);
            contentValues.put("gender", gender);
            contentValues.put("orientation", orientation);
            contentValues.put("phonenumber", phonenumber);
            contentValues.put("tagline", tagline);
            contentValues.put("deviceid", deviceid);

            db.insert("contacts", null, contentValues);
        }
        else {
            contentValues.put("name", firstname);
            contentValues.put("deviceid", deviceid);

            db.insert("users", null, contentValues);
        }
        return true;
    }
    public Cursor getData(int id, int mode){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        if(mode == 0) {
             res = db.rawQuery("select * from contacts where id=" + id + "", null);
        } else {
             res =  db.rawQuery( "select * from users where id="+id+"", null );
        }
        return res;
    }
    public int numberOfRows(int mode){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows;
        if(mode == 0) {
            numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        } else {
            numRows = (int) DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);
        }
        return numRows;
    }
    public boolean updateContact (Integer id, String lastname, String firstname, String age, String gender, String orientation, String phonenumber, String tagline, String deviceid, int mode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(mode == 0) {
            contentValues.put("lastname", lastname);
            contentValues.put("firstname", firstname);
            contentValues.put("age", age);
            contentValues.put("gender", gender);
            contentValues.put("orientation", orientation);
            contentValues.put("phonenumber", phonenumber);
            contentValues.put("tagline", tagline);
            contentValues.put("deviceid", deviceid);
            db.update("contacts", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        } else {
            contentValues.put("deviceid", deviceid);
            contentValues.put("name", firstname);
            db.update("users", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        }
        return true;
    }

    public Integer deleteContact (Integer id, int mode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if (mode == 0) {
            return db.delete("contacts",
                    "id = ? ",
                    new String[]{Integer.toString(id)});
        } else {
            return db.delete("users",
                    "id = ? ",
                    new String[] { Integer.toString(id) });
        }
    }
    public ArrayList getAllContacts(int mode)
    {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        if(mode == 0) {
            Cursor res = db.rawQuery("select * from contacts", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                int cIndex = res.getColumnIndex(CONTACTS_COLUMN_LASTNAME);
                if (cIndex >= 0) {
                    array_list.add(res.getString(cIndex));
                }
                res.moveToNext();
            }
        } else {
            Cursor res =  db.rawQuery( "select * from users", null );
            res.moveToFirst();
            while(res.isAfterLast() == false){
                int cIndex = res.getColumnIndex(USERS_COLUMN_NAME);
                if(cIndex >=0)
                {
                    array_list.add(res.getString(cIndex));
                }
                res.moveToNext();
            }
        }
        return array_list;
    }


    public String getLastName(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select lastname from contacts", null );
        res.moveToFirst();
        String temp_last = res.getString(0);
        return temp_last;
    }

    public String getFirstName(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select firstname from contacts", null );
        res.moveToFirst();
        String temp_last = res.getString(0);
        return temp_last;
    }

    public String getAge(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select age from contacts", null );
        res.moveToFirst();
        String temp_last = res.getString(0);
        return temp_last;
    }

    public String getGender(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select gender from contacts", null );
        res.moveToFirst();
        String temp_last = res.getString(0);
        return temp_last;
    }

    public String getOrientation(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select orientation from contacts", null );
        res.moveToFirst();
        String temp_last = res.getString(0);
        return temp_last;
    }

    public String getPhoneNumber(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select phonenumber from contacts", null );
        res.moveToFirst();
        String temp_last = res.getString(0);
        return temp_last;
    }

    public String getTagLine(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select tagline from contacts", null);
        res.moveToFirst();
        String temp_last = res.getString(0);
        return temp_last;
    }
}
