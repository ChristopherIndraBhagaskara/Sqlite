package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Mhs> mhsList;
    Mhs mm;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inpName = (EditText) findViewById(R.id.inpName);
        EditText inpNim = (EditText) findViewById(R.id.inpNim);
        EditText inpNoHp = (EditText) findViewById(R.id.inpNoHp);
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        Button btnList = (Button) findViewById(R.id.btnList);

        mhsList = new ArrayList<Mhs>();
        isEdit = false;

        Intent intent_main = getIntent();
        if(intent_main.hasExtra("mhsData")){
            mm = intent_main.getExtras().getParcelable("mhsData");
            inpName.setText(mm.getNama());
            inpNim.setText(mm.getNim());
            inpNoHp.setText(mm.getNoHp());

            isEdit = true;
            btnSubmit.setBackgroundColor(Color.GREEN);
            btnSubmit.setText("Update");
        }

        DbHelper db = new DbHelper(getApplicationContext());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inpName.getText().toString();
                String nim = inpNim.getText().toString();
                String phone = inpNoHp.getText().toString();

                if  (name.isEmpty() || nim.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Fill input Field!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean stts;
                    mhsList = db.list();
                    if  (mhsList.size() >= 5) {
                        Toast.makeText(getApplicationContext(), "Data tidak boleh melebihi 5!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!isEdit) {
                            mm = new Mhs(-1, name, nim, phone);
                            stts = db.simpan(mm);

                            inpName.setText("");
                            inpNim.setText("");
                            inpNoHp.setText("");
                        } else {
                            mm = new Mhs(mm.getId(), name, nim, phone);
                            stts = db.ubah(mm);
                        }

                        if(stts){
                            Toast.makeText(getApplicationContext(), "Data Saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Failed to Save!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    // intentList.putParcelableArrayListExtra("mhsList", mhsList);
                    // startActivity(intentList);
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mhsList = db.list();

                if  (mhsList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Belum ada data!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentList = new Intent(MainActivity.this, ListMhsActivity.class);
                    intentList.putParcelableArrayListExtra("mhsList", mhsList);
                    startActivity(intentList);
                }
            }
        });
    }
}