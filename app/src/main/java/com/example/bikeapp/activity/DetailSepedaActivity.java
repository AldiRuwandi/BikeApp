package com.example.bikeapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bikeapp.R;
import com.example.bikeapp.helper.DataHelper;

public class DetailSepedaActivity extends AppCompatActivity {

    protected Cursor cursor;
    String sMerk,sHarga,sGambar;
    DataHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sepeda);

        Bundle terima = getIntent().getExtras();

        dbHelper =new DataHelper(this);
        Intent intent =getIntent();

        String merk = terima.getString("merk");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from sepeda where merk ='" + merk + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            sMerk = cursor.getString(0);
            sHarga =cursor.getString(1);
        }

        if (sMerk.equals("Polygon")){
            sGambar = "polygon";
        }else if (sMerk.equals("Atlantis")){
            sGambar = "atlantis";
        }else if (sMerk.equals("Aviator")){
            sGambar = "aviator";
        }else if (sMerk.equals("Avand")){
            sGambar = "avand";
        }else if (sMerk.equals("Odessy")){
            sGambar = "odessy";
        }else if (sMerk.equals("Pacific")){
            sGambar = "pacific";
        }else if (sMerk.equals("Senator")){
            sGambar = "senator";
        }else if (sMerk.equals("United")){
            sGambar = "united";
        }else if (sMerk.equals("Wimcycle")){
            sGambar = "wimcycle";
        }

        ImageView ivGambar =findViewById(R.id.ivSepeda);
        TextView tvMerk = findViewById(R.id.JSepeda);
        TextView tvHarga = findViewById(R.id.JHarga);

        tvMerk.setText(sMerk);
        ivGambar.setImageResource(getResources().getIdentifier(sGambar, "drawable", getPackageName()));
        tvHarga.setText("Rp.  " + sHarga);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar =findViewById(R.id.tbDetailSepeda);
        toolbar.setTitle("Detail Sepeda");
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
}
