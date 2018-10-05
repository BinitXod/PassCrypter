package com.fear.passcrypter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.fear.passcrypter.Credentials;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "credentials.db";
    public static final String TABLE_CREDENTIALS = "credentials";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "pass";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SWITCH = "switchcond";
    public static final String COLUMN_KEY = "key";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        String query = "CREATE TABLE " + TABLE_CREDENTIALS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_TITLE + " TEXT ," +
                COLUMN_USERNAME + " TEXT ," +
                COLUMN_PASSWORD + " TEXT ," +
                COLUMN_SWITCH + " INTEGER DEFAULT 0 ," +
                COLUMN_KEY + " INTEGER DEFAULT 0 " +
                ");";
        sqldb.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int i, int i1) {
        sqldb.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDENTIALS);
        onCreate(sqldb);
    }

    //Add new row to db
    public void addCredential(Credentials credential){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, credential.get_title());
        values.put(COLUMN_USERNAME, credential.get_username());
        values.put(COLUMN_PASSWORD, credential.get_pass());
        int myInt = (credential.get_switchcond()) ? 1 : 0;
        values.put(COLUMN_SWITCH, myInt);
        values.put(COLUMN_KEY, credential.get_key());
        SQLiteDatabase sqldb = getWritableDatabase();
        sqldb.insert(TABLE_CREDENTIALS, null, values);
        sqldb.close();
    }

    /*Get a credential
    public int getCredential(int id){
        SQLiteDatabase sqldb = getWritableDatabase();
        sqldb.execSQL("SELECT FROM " + TABLE_CREDENTIALS + " WHERE " + COLUMN_ID + "=\"" + credentialId + "\";");

    } */

    //Change a credential
    public void setaCredential(String a, String b){
        SQLiteDatabase sqldb = getWritableDatabase();
        sqldb.execSQL("UPDATE " + TABLE_CREDENTIALS + " SET " + COLUMN_PASSWORD + "=\"" + a + "\"" + " WHERE " + COLUMN_TITLE + "=\"" + b + "\";");
    }

    public void setaSwitch(boolean a, String b){
        int myInt = (a) ? 1 : 0;
        SQLiteDatabase sqldb = getWritableDatabase();
        sqldb.execSQL("UPDATE " + TABLE_CREDENTIALS + " SET " + COLUMN_SWITCH + "=" + myInt + " WHERE " + COLUMN_TITLE + "=\"" + b + "\";");
    }

    public void setaKey(int k, String b){
        SQLiteDatabase sqldb = getWritableDatabase();
        sqldb.execSQL("UPDATE " + TABLE_CREDENTIALS + " SET " + COLUMN_KEY + "=" + k + " WHERE " + COLUMN_TITLE + "=\"" + b + "\";");
    }

    //Get all credentials
    public List<Credentials> getAllCredentials(){
        List<Credentials> credentialsList = new ArrayList<Credentials>();

        //Select all query
        String selectQuery = "SELECT * FROM " + TABLE_CREDENTIALS + " WHERE 1";

        SQLiteDatabase sqldb = this.getWritableDatabase();
        Cursor cursor = sqldb.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                Credentials credential = new Credentials();
                credential.set_title(cursor.getString(1));
                credential.set_username(cursor.getString(2));
                credential.set_pass((cursor.getString(3)));
                credential.set_switchcond(cursor.getInt(4)>0);
                credential.set_key(cursor.getInt(5));

                //Add to list
                credentialsList.add(credential);
            } while (cursor.moveToNext());
            }
        return credentialsList;
        }

    //Delete from db
    public void deleteCredential(String title){
        SQLiteDatabase sqldb = getWritableDatabase();
        sqldb.execSQL("DELETE FROM " + TABLE_CREDENTIALS + " WHERE " + COLUMN_TITLE + "=\"" + title + "\";");
    }

}
