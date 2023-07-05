package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_NIM = "nim";
    public static final String COLUMN_NOHP = "nohp";
    public static final String TABLE_NAME = "mhsdb";

    public DbHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String nmtabel = TABLE_NAME;
        String statement = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAMA + " TEXT, " + COLUMN_NIM + " TEXT, " + COLUMN_NOHP + " TEXT)";
        db.execSQL(statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean simpan(Mhs mm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAMA, mm.getNama());
        cv.put(COLUMN_NIM, mm.getNim());
        cv.put(COLUMN_NOHP, mm.getNoHp());

        long stts = db.insert(TABLE_NAME, null, cv);
        if (stts == -1) {
            return false;
        }

        return true;
    }

    public ArrayList<Mhs> list() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Mhs> mhsList = new ArrayList<>();

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String nim = cursor.getString(2);
                String phone = cursor.getString(3);

                Mhs mm = new Mhs(id, name, nim, phone);
                mhsList.add(mm);
            }
        }

        return mhsList;
    }

    public boolean hapus(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowAffected = db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});

        if(rowAffected > 0) {
            return true;
        }

        return false;
    }

    public boolean ubah (Mhs mm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAMA, mm.getNama());
        cv.put(COLUMN_NIM, mm.getNim());
        cv.put(COLUMN_NOHP, mm.getNoHp());
        int rowAffected = db.update(TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(mm.getId())});
        if (rowAffected > 0) {
            return true;
        }

        return false;

    }

}
