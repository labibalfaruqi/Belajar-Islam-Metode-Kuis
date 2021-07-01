package com.ldev.bimq;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IsiSoalPilihanGandaActivity extends AppCompatActivity {

    private static final String TAG = "CheckerKPG";
    private Button KPGButtonPilihanSatu, KPGButtonPilihanDua, KPGButtonPilihanTiga, KPGButtonPilihanEmpat;
    private TextView KPGTextViewJudul, KPGTextViewNomorSoal, KPGTextViewSoal;


    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_soal_pilihan_ganda);

        ImageButton KPGButtonBack = findViewById(R.id.KPGButtonBack);
        KPGTextViewJudul = findViewById(R.id.KPGTextViewJudul);
        KPGTextViewNomorSoal = findViewById(R.id.KPGTextViewNomorSoal);
        KPGTextViewSoal = findViewById(R.id.KPGTextViewSoal);
        KPGButtonPilihanSatu = findViewById(R.id.KPGButtonPilihanSatu);
        KPGButtonPilihanDua = findViewById(R.id.KPGButtonPilihanDua);
        KPGButtonPilihanTiga = findViewById(R.id.KPGButtonPilihanTiga);
        KPGButtonPilihanEmpat = findViewById(R.id.KPGButtonPilihanEmpat);

        Intent intent = getIntent();
        Context context = getApplicationContext();
        dbHandler = new DBHandler(context);

        String levelSoal = intent.getStringExtra("levelSoal");
        String tipeSoal = intent.getStringExtra("tipeSoal");
        String judulSoal = intent.getStringExtra("judulSoal");

        String judul = "Kuis " + tipeSoal + " " + judulSoal;
        KPGTextViewJudul.setText(judul);

        Cursor cursorKonten = null;
        int countKonten = 0;

        String condition = Table.Soal.COL_TIPE + "='" + tipeSoal + judulSoal
                + "' AND " + Table.Soal.COL_LEVEL_SOAL + "='" + levelSoal.replace("Level ", "") + "'";
        cursorKonten = dbHandler.getData(Table.Soal.TABLE_NAME, "*", condition);

        List<List<String>> listOfLists = null;

        if (cursorKonten != null && cursorKonten.moveToFirst()) {
            listOfLists = new ArrayList<>(cursorKonten.getCount());
            for (int i = 0; i < cursorKonten.getCount(); i++) {
                listOfLists.add(new ArrayList<>());
            }
            do {
                listOfLists.get(countKonten).add(String.valueOf(cursorKonten.getString(1))); //soal
                listOfLists.get(countKonten).add(String.valueOf(cursorKonten.getString(3))); //jawaban
                listOfLists.get(countKonten).add(String.valueOf(cursorKonten.getString(4))); //pilihan jawaban
                countKonten++;
            } while (cursorKonten.moveToNext());
        }
        Integer[] randomCountKonten = new Integer[countKonten];
        for (int i = 0; i < randomCountKonten.length; i++) {
            randomCountKonten[i] = i;
        }
        try {
            Collections.shuffle(Arrays.asList(randomCountKonten));
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        AtomicInteger pageKBS = new AtomicInteger();
        pageKBS.getAndIncrement();
        AtomicInteger progressBenar = new AtomicInteger();
        AtomicInteger progressSalah = new AtomicInteger();

        if (pageKBS.get() == 1) { //soal pertama
            int finalPageKBS = pageKBS.get();
            String stringPageKBS = finalPageKBS + ". ";
            KPGTextViewNomorSoal.setText(stringPageKBS);
            if (listOfLists != null) {
                KPGTextViewSoal.setText(listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(0)); //soal
                setPilihanJawaban(listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(2)); //pilihan jawaban
            }
        }
        int finalCountKonten = countKonten;
        List<List<String>> finalListOfLists = listOfLists;
        KPGButtonPilihanSatu.setOnClickListener(v -> {
            soalSelanjutnya(pageKBS, finalCountKonten, finalListOfLists, randomCountKonten, (String) KPGButtonPilihanSatu.getText(), progressBenar, progressSalah, tipeSoal, levelSoal, judulSoal);
        });
        KPGButtonPilihanDua.setOnClickListener(v -> {
            soalSelanjutnya(pageKBS, finalCountKonten, finalListOfLists, randomCountKonten, (String) KPGButtonPilihanDua.getText(), progressBenar, progressSalah, tipeSoal, levelSoal, judulSoal);
        });
        KPGButtonPilihanTiga.setOnClickListener(v -> {
            soalSelanjutnya(pageKBS, finalCountKonten, finalListOfLists, randomCountKonten, (String) KPGButtonPilihanTiga.getText(), progressBenar, progressSalah, tipeSoal, levelSoal, judulSoal);
        });
        KPGButtonPilihanEmpat.setOnClickListener(v -> {
            soalSelanjutnya(pageKBS, finalCountKonten, finalListOfLists, randomCountKonten, (String) KPGButtonPilihanEmpat.getText(), progressBenar, progressSalah, tipeSoal, levelSoal, judulSoal);
        });

        KPGButtonBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void setPilihanJawaban(String pilihanJawabanDatabase) {
        String[] pilihanJawaban = pilihanJawabanDatabase.split("pemisahArray");
        try {
            Collections.shuffle(Arrays.asList(pilihanJawaban));
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        KPGButtonPilihanSatu.setText(pilihanJawaban[0]);
        KPGButtonPilihanDua.setText(pilihanJawaban[1]);
        KPGButtonPilihanTiga.setText(pilihanJawaban[2]);
        KPGButtonPilihanEmpat.setText(pilihanJawaban[3]);
//        return pilihanJawaban;
    }

    private void soalSelanjutnya(AtomicInteger pageKBS, int countKonten, List<List<String>> listOfLists
            , Integer[] randomCountKonten, String pilihanJawaban, AtomicInteger progressBenar, AtomicInteger progressSalah, String tipeSoal, String levelSoal, String judulSoal) {
        int finalPageKBS = pageKBS.get();

        if (finalPageKBS < countKonten) {
            finalPageKBS = pageKBS.incrementAndGet();
            String stringPageKBS = finalPageKBS + ". ";
            KPGTextViewNomorSoal.setText(stringPageKBS);
            KPGTextViewSoal.setText(listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(0)); //soal
            setPilihanJawaban(listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(2)); //pilihan jawaban
            if (listOfLists.get(randomCountKonten[finalPageKBS - 2]).get(1).equals(pilihanJawaban))
                progressBenar.getAndIncrement();
            else progressSalah.getAndIncrement();
            Log.d(TAG, "#1: pageKBS: " + pageKBS + " progressBenar: " + progressBenar + " progressSalah: " + progressSalah + " jawaban dari database: " + listOfLists.get(randomCountKonten[finalPageKBS - 2]).get(1) + " jawaban user: " + pilihanJawaban);
        } else {
            if (listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(1).equals(pilihanJawaban))
                progressBenar.getAndIncrement();
            else progressSalah.getAndIncrement();
            String tipeSoalKembali;
            if (tipeSoal.contains("Materi")) tipeSoalKembali = "Materi";
            else tipeSoalKembali = tipeSoal;

            progress(progressBenar, progressSalah, levelSoal, tipeSoal, judulSoal);
            Log.d(TAG, "#2: pageKBS: " + pageKBS + " progressBenar: " + progressBenar + " progressSalah: " + progressSalah + " jawaban dari database: " + listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(1));

            Intent kembali = new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            kembali.putExtra("fragmentPilihan", tipeSoalKembali);
            startActivity(kembali);
            finish();
        }
    }

    private void progress(AtomicInteger progressBenar, AtomicInteger progressSalah, String levelSoal, String tipeSoal, String judulSoal) {
//        int countKonten = 0;
        int trimmedLevelSoal = Integer.parseInt(levelSoal.replace("Level ", ""));
//        String conditionStatistik = Table.Statistik.COL_TIPE + "='" + tipeSoal + judulSoal + "' AND " + Table.Statistik.COL_LEVEL_SOAL + "='" + trimmedLevelSoal + "'";
//        String conditionProgress = Table.Progress.COL_TIPE + "='" + tipeSoal + judulSoal + "'";

        int benar = 0;
        int salah = 0;
        if (progressBenar != null) benar = Integer.parseInt(String.valueOf(progressBenar));
        if (progressSalah != null) salah = Integer.parseInt(String.valueOf(progressSalah));
//        Cursor cursorStatistik = dbHandler.getData(Table.Statistik.TABLE_NAME, "*", conditionStatistik);
//        if (cursorStatistik != null && cursorStatistik.moveToFirst()) {
//            //update instead
//            dbHandler.updateDataStatistik(trimmedLevelSoal, benar, salah, tipeSoal + judulSoal);
//        } else {
        //insert instead
        dbHandler.insertUpdateDataStatistik(trimmedLevelSoal, benar, salah, tipeSoal, judulSoal);
        Log.d(TAG, trimmedLevelSoal + benar + salah + tipeSoal + judulSoal);
//        }

        String conditionProgress = Table.Progress.COL_TIPE + "='" + tipeSoal + judulSoal + "'";
        Cursor cursorProgress = dbHandler.getData(Table.Progress.TABLE_NAME, "*", conditionProgress);
        if (cursorProgress != null && cursorProgress.moveToFirst()) {
            //update instead
            int levelProgressDatabase = Integer.parseInt(cursorProgress.getString(1).replace("Level ", ""));
            Log.d(TAG, "levelProgressDatabase: " + levelProgressDatabase + " levelProgress: " + trimmedLevelSoal);
            if (levelProgressDatabase < trimmedLevelSoal) {
                dbHandler.updateDataProgress(String.valueOf(trimmedLevelSoal), tipeSoal + judulSoal);
            }
        } else {
            dbHandler.insertDataProgress(String.valueOf(trimmedLevelSoal), tipeSoal + judulSoal);
        }
        //insert instead
//        dbHandler.insertUpdateDataProgress(String.valueOf(1), tipeSoal, judulSoal, trimmedLevelSoal);
        Log.d(TAG, String.valueOf(1) + tipeSoal + judulSoal + trimmedLevelSoal);
//        }
//        //ngecek aja buat testing udah masuk apa belom datanya
//        Cursor cursorTestingStatistik = dbHandler.getData(Table.Statistik.TABLE_NAME, "*", conditionStatistik);
//        List<String> printTestingStatistik = new ArrayList<>();
//        if (cursorTestingStatistik != null && cursorTestingStatistik.moveToFirst()) {
//            do {
//                printTestingStatistik.add(String.valueOf(cursorTestingStatistik.getCount()));
//                for (int i = 0; i < cursorTestingStatistik.getColumnCount(); i++) {
//                    printTestingStatistik.add(cursorTestingStatistik.getString(i));
//                }
//            } while (cursorTestingStatistik.moveToNext());
//        }
//        Log.d(TAG, String.valueOf(printTestingStatistik));
//
//        //ngecek aja buat testing udah masuk apa belom datanya
//        Cursor cursorTestingProgress = dbHandler.getData(Table.Progress.TABLE_NAME, "*", conditionProgress);
//        List<String> printTestingProgress = new ArrayList<>();
//        if (cursorTestingProgress != null && cursorTestingProgress.moveToFirst()) {
//            do {
//                printTestingProgress.add(String.valueOf(cursorTestingProgress.getCount()));
//                for (int i = 0; i < cursorTestingProgress.getColumnCount(); i++) {
//                    printTestingProgress.add(cursorTestingProgress.getString(i));
//                }
//            } while (cursorTestingProgress.moveToNext());
//        }
//        Log.d(TAG, String.valueOf(printTestingProgress));
    }
}