package com.example.bikeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bikeapp.R;
import com.example.bikeapp.helper.DataHelper;

public class DaftarSepedaActivity extends AppCompatActivity {

    String[] daftar;
    ListView listView1;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbCenter;
    public static DaftarSepedaActivity daftarSepedaActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_sepeda);

        daftarSepedaActivity = this;
        dbCenter = new DataHelper(this);

        RefreshList();
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbInfoSepeda);
        toolbar.setTitle("Informasi Daftar Sepeda");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void RefreshList() {
        SQLiteDatabase db =dbCenter.getReadableDatabase();
        cursor =db.rawQuery("SELECT * FROM sepeda", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(0);
        }
        listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        listView1.setSelected(true);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection =daftar[position];
                Intent i =new Intent(DaftarSepedaActivity.this, DetailSepedaActivity.class);
                i.putExtra("merk", selection);
                startActivity(i);
            }
        });

        ((ArrayAdapter)listView1.getAdapter()).notifyDataSetInvalidated();
    }
}
