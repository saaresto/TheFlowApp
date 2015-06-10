package com.ilyaissakin.theflowapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by Ilya on 09.06.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, ConstantStrings.DATABASE_NAME, null, 1);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public boolean recordExists(String pk) {
        String query = "SELECT * FROM favorites WHERE " +
                ConstantStrings.DATABASE_COLUMNS[0] +
                "=\"" +
                pk +
                "\";";
        int count = this.getReadableDatabase().rawQuery(query, null).getCount();

        return count > 0;
    }

    public HashMap<String, String> getFavsAsHashMap() {
        HashMap<String, String> values = new HashMap<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM favorites;", null);

        if (cursor.moveToFirst()) {
            do {
                values.put(
                        cursor.getString(cursor.getColumnIndex(ConstantStrings.DATABASE_COLUMNS[0])),
                        cursor.getString(cursor.getColumnIndex(ConstantStrings.DATABASE_COLUMNS[1]))
                );
            } while (cursor.moveToNext());
        }

        cursor.close();
        return values;
    }

    public boolean insert(String link, String title) {
        if (recordExists(link))
            return false;

        ContentValues cv = new ContentValues();
        cv.put(ConstantStrings.DATABASE_COLUMNS[0], link);
        cv.put(ConstantStrings.DATABASE_COLUMNS[1], title);
        long result = this.getWritableDatabase().insert("favorites", null, cv);

        return result != -1;
    }

    public boolean delete(String pk) {
        return recordExists(pk) && this.getWritableDatabase().delete("favorites", ConstantStrings.DATABASE_COLUMNS[0] + "=?", new String[]{pk}) > 0;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE favorites (" +
                "link VARCHAR(100) PRIMARY KEY," +
                "title VARCHAR(100)" +
                ");";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
