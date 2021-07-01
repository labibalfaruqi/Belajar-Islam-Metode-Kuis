package com.ldev.bimq;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ldev.bimq.sqlite.DBHandler;
import com.ldev.bimq.sqlite.Table;

public class StartPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_play);

        DBHandler dbHandler = new DBHandler(getApplicationContext());
        String TAG = "CheckerSP";

        Intent intent = getIntent();
        TextView SPTextViewJudul = findViewById(R.id.SPTextViewJudul);
        TextView SPTextViewJudulMateri = findViewById(R.id.SPTextViewJudulMateri);
        TextView SPTextViewJudulLevel = findViewById(R.id.SPTextViewJudulLevel);
        ImageButton SPButtonBack = findViewById(R.id.SPButtonBack);
        SPButtonBack.setOnClickListener(v -> {
            onBackPressed();
        });
        Button SPButtonMilihLevel = findViewById(R.id.SPButtonMilihLevel);
        ImageButton SPButtonPlay = findViewById(R.id.SPButtonPlay);

        String tipeSoal = intent.getStringExtra("tipeSoal");
        String judulSoal = intent.getStringExtra("judulSoal");
        String levelSoal = intent.getStringExtra("levelSoal");

        //cek progress, pake yg progress+1
        String conditionProgress = Table.Progress.COL_TIPE + "= '" + tipeSoal + judulSoal + "'";
        Cursor cursorProgress = dbHandler.getData(Table.Progress.TABLE_NAME, Table.Progress.COL_PROGRESS, conditionProgress);
        int progress = 1;
        if (cursorProgress != null && cursorProgress.moveToFirst()) {
            do {
                progress = Integer.parseInt(cursorProgress.getString(0)) + 1;
                if (progress == 6) progress = 5;
                Log.d(TAG, "masuk ke #1SP if" + cursorProgress + " count: " + cursorProgress.getCount());
            } while (cursorProgress.moveToNext());
        }
        String stringProgress = "Level " + progress;
        Log.d(TAG, "masuk ke #2SP" + progress + " stringProgress: " + stringProgress);

        //kalau pilihan levelnya dari pemilihanLevelActivity
        if (levelSoal != null) {
            SPTextViewJudulLevel.setText(levelSoal);
        } else {
            SPTextViewJudulLevel.setText(stringProgress);
            levelSoal = stringProgress;
        }

        if (tipeSoal != null && judulSoal != null) {
            SPTextViewJudul.setText(tipeSoal);
            SPTextViewJudulMateri.setText(judulSoal);
        }
//        int finalProgress = progress;
        SPButtonMilihLevel.setOnClickListener(v -> {
            Intent intentPindahHalaman = new Intent(getApplicationContext(), PemilihanLevelActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intentPindahHalaman.putExtra("tipeSoal", tipeSoal);
            intentPindahHalaman.putExtra("judulSoal", judulSoal);
            intentPindahHalaman.putExtra("levelSoal", stringProgress);
            Log.d(TAG, "tipeSoal: " + tipeSoal + " judulSoal: " + judulSoal + " levelSoal: " + stringProgress);
            startActivity(intentPindahHalaman);
        });
        if (tipeSoal != null) {
            if (tipeSoal.equals("Pilihan Ganda")) {
                String finalLevelSoal = levelSoal;
                SPButtonPlay.setOnClickListener(v -> {
                    Intent intentPindahHalaman = new Intent(getApplicationContext(), IsiSoalPilihanGandaActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentPindahHalaman.putExtra("tipeSoal", tipeSoal);
                    intentPindahHalaman.putExtra("judulSoal", judulSoal);
                    intentPindahHalaman.putExtra("levelSoal", finalLevelSoal);
                    startActivity(intentPindahHalaman);
                });
            } else if (tipeSoal.equals("Benar Salah") || tipeSoal.contains("Materi")) {
                String finalLevelSoal = levelSoal;
                SPButtonPlay.setOnClickListener(v -> {
                    Intent intentPindahHalaman = new Intent(getApplicationContext(), KontenBenarSalah_MateriActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentPindahHalaman.putExtra("tipeSoal", tipeSoal);
                    intentPindahHalaman.putExtra("judulSoal", judulSoal);
                    intentPindahHalaman.putExtra("levelSoal", finalLevelSoal);
                    startActivity(intentPindahHalaman);
                });
            }
        }
    }
}