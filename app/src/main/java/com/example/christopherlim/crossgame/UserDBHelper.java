package com.example.christopherlim.crossgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sean on 12/7/2014.
 */
public class UserDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COLUMN_DEVICEID = "deviceid";
    public static final String USERS_COLUMN_NAME = "name";

    private HashMap hp;

    public UserDBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String CREATE_USERS_TABLE= "CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY, " +
                "deviceid TEXT, " +
                "name TEXT )"
                ;

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean insertUser  (String deviceid, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("deviceid", deviceid);

        db.insert("users", null, contentValues);
        return true;
    }
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where id="+id+"", null );
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);
        return numRows;
    }
    public boolean updateContact (Integer id, String deviceid, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("deviceid", deviceid);
        contentValues.put("name", name);
        db.update("users", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public ArrayList getAllContacts()
    {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
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
        return array_list;
    }
}
