package com.example.bikeapp.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="rentalsepeda.db";
    private static final int DATABASE_VERSION =1;

    public DataHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_key = ON");
        db.execSQL("create table penyewa(" + "nama text," + "alamat text," + "no_hp text," + "primary key(nama)" + ");" +"");
        db.execSQL("create table sepeda(" + "merk text," + "harga int," + "primary key (merk)" + ");" + "");
        db.execSQL("create table sewa(" + "merk text," + "nama text," + "promo int," + "lama int," + "total double," + "foreign key (merk) references sepeda(merk)," + "foreign key(nama)references penyewa(nama)" +");" + "");
        //db.execSQL("create table login(" + "id text," + "username text," + "password text," + "primary key(id)" +");");

        db.execSQL("insert into sepeda values(" + "'Wimcycle'," + "550000" + ");" + "");

        db.execSQL("insert into sepeda values(" + "'Polygon'," + "400000" + ");" + "");

        db.execSQL("insert into sepeda values(" + "'United'," + "370000" + ");" + "");

        db.execSQL("insert into sepeda values(" + "'Odessy'," + "260000" + ");" + "");

        db.execSQL("insert into sepeda values(" + "'Atlantis'," + "250000" + ");" + "");

        db.execSQL("insert into sepeda values(" + "'Aviator'," + "150000" + ");" + "");

        db.execSQL("insert into sepeda values(" + "'Pacific'," + "300000" + ");" + "");

        db.execSQL("insert into sepeda values(" + "'Avand'," + "100000" + ");" + "");

        db.execSQL("insert into sepeda values(" + "'Senator'," + "200000" + ");" + "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<String> getAllCategories(){
        List<String> categories = new ArrayList<String>();
        String selecQuery = "select * from sepeda";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selecQuery, null);

        if (cursor.moveToFirst()){
            do {
                categories.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return categories;
    }
}
