package com.example.yah.androidlabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Yah on 2017-02-08.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME ="Messages.db";
    public static final String TABLE_NAME = "MESSAGES";
    private static int VERSION_NUM =2;
    private final Context mCtx;

    private ChatDatabaseHelper mDBHelper;
    SQLiteDatabase mDb=getWritableDatabase();

    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "Message";

    public static final String[] Columns = new String[]{
            KEY_ID,
            KEY_MESSAGE
    };

    private static final String Create_Table = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MESSAGE + " text" +")";

    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        this.mCtx=ctx;
    }


    public Cursor getMessages(){
        return mDb.query(TABLE_NAME, Columns, null, null, null, null,null);
    }



    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Create_Table);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }



}
