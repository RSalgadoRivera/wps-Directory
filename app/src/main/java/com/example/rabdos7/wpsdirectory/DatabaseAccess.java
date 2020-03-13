package com.example.rabdos7.wpsdirectory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rabdos7 on 24/04/18.
 */

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }


    public static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAccess(context);
        }return instance;

    }

    public void open(){
        this.database = openHelper.getWritableDatabase();
    }

    public void close(){
        if (database != null){
            this.database.close();
        }
    }

    public String getFloor(String mac){

        Cursor cursor = null;
        String piso = "";
        try {
            String query = "SELECT piso FROM ap WHERE mac=?";
            cursor = database.rawQuery(query, new String[] {mac + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                piso = cursor.getString(cursor.getColumnIndex("piso"));
            }
            return piso;
        }catch (Exception e){
            System.out.println(e);
        }
        return piso;
    }

}
