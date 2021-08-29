package com.example.bikeapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bikeapp.R;
import com.example.bikeapp.helper.DataHelper;

import java.util.List;

public class SewaSepedaActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    EditText nama,alamat,no_hp, lama;
    RadioGroup promo;
    RadioButton weekDay, weekEnd;
    Button selesai;

    String sNama,sAlamat, sNo, sMerk, sLama;
    double dPromo;
    int iLama,iPromo,iHarga;
    double dTotal;

    private Spinner spinner;
    DataHelper dbCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_sepeda);

        dbCenter = new DataHelper(this);

        spinner = findViewById(R.id.spinner);
        selesai = findViewById(R.id.selesaiHitung);
        nama = findViewById(R.id.eTNama);
        alamat = findViewById(R.id.eTAlamat);
        no_hp = findViewById(R.id.eTHP);
        promo = findViewById(R.id.promoGroup);
        weekDay = findViewById(R.id.rbWeekDay);
        weekEnd = findViewById(R.id.rbWeekEnd);
        lama = findViewById(R.id.eTLamaSewa);

        spinner.setOnItemSelectedListener(this);

        loadSpinnerData();

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sNama = nama.getText().toString();
                sAlamat = alamat.getText().toString();
                sNo = no_hp.getText().toString();
                sLama = lama.getText().toString();
                if (sNama.isEmpty() || sAlamat.isEmpty() || sNo.isEmpty() || sLama.isEmpty()){
                    Toast.makeText(SewaSepedaActivity.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (weekDay.isChecked()){
                    dPromo = 0.1;
                }else if (weekEnd.isChecked()){
                    dPromo = 0.2;
                }

                if (sMerk.equals("Polygon")){
                    iHarga = 200000;
                }else if (sMerk.equals("Atlantis")){
                    iHarga = 250000;
                }else if (sMerk.equals("Avand")){
                    iHarga = 300000;
                }else if (sMerk.equals("Aviator")){
                    iHarga = 350000;
                }else if (sMerk.equals("Odessy")){
                    iHarga = 400000;
                }else if (sMerk.equals("Pacific")){
                    iHarga = 450000;
                }else if (sMerk.equals("Senator")){
                    iHarga = 500000;
                }else if (sMerk.equals("United")){
                    iHarga = 550000;
                }else if (sMerk.equals("Wimcycle")){
                    iHarga = 600000;
                }

                iLama = Integer.parseInt(sLama);
                iPromo =(int)(dPromo * 100);
                dTotal = (iHarga * iLama) - (iHarga * iLama * dPromo);

                SQLiteDatabase dbHelper = dbCenter.getWritableDatabase();
                dbHelper.execSQL("INSERT INTO penyewa (nama, alamat, no_hp) VALUES ('" + sNama + "','" + sAlamat +"','" + sNo + "')");

                dbHelper.execSQL("INSERT INTO sewa (merk, nama, promo, lama, total) VALUES ('" + sMerk + "','" + sNama +"','" + iPromo + "','" + iLama + "','" + dTotal + "')");

                PenyewaActivity.p.RefreshList();
                finish();
            }
        });

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbSewaSepeda);
        toolbar.setTitle("Sewa Sepeda");
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

    private void loadSpinnerData() {
        DataHelper db =new DataHelper(getApplicationContext());
        List<String> categories = db.getAllCategories();

        ArrayAdapter<String> dataAdapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sMerk = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
