package com.example.bikeapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.bikeapp.R;
import com.example.bikeapp.helper.DataHelper;

public class PenyewaActivity extends AppCompatActivity {

    String[] daftar;
    int[] id;
    ListView listView1;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbCenter;
    public static PenyewaActivity p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyewa);

        Button tambah =findViewById(R.id.tambahPenyewa);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PenyewaActivity.this, SewaSepedaActivity.class);
                startActivity(i);
            }
        });

        p = this;
        dbCenter = new DataHelper(this);

        RefreshList();
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbPenyewa);
        toolbar.setTitle("Daftar Penyewa");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void RefreshList() {
        SQLiteDatabase db = dbCenter.getReadableDatabase();
        cursor =db.rawQuery("SELECT * FROM penyewa", null);
        daftar =new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            daftar[i]=cursor.getString(0);
        }

        listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        listView1.setSelected(true);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                final String selection = daftar[position];
                final CharSequence[] dialogItem = {"Lihat Data", "Hapus Data"};
                AlertDialog.Builder builder =new AlertDialog.Builder(PenyewaActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0 : {
                                Intent i = new Intent(PenyewaActivity.this, DetailPenyewaActivity.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break;
                            }
                            case 1 : {
                                SQLiteDatabase db =dbCenter.getWritableDatabase();
                                db.execSQL("DELETE FROM penyewa where nama ='" + selection + "'");
                                db.execSQL("DELETE FROM sewa where nama ='" + selection + "'");
                                RefreshList();
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)listView1.getAdapter()).notifyDataSetChanged();
    }
}
