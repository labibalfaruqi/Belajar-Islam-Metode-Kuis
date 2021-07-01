package com.ldev.bimq;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

public class KontenBenarSalah_MateriActivity extends AppCompatActivity {

    private static final String TAG = "CheckerKBS";
    //    TextView KBSTextViewNomorSoal = findViewById(R.id.KBSTextViewNomorSoal);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konten_benar_salah__materi);

//        Intent intent = getIntent();

        TextView KBSTextViewNomorSoal = findViewById(R.id.KBSTextViewNomorSoal);
        TextView KBSTextViewSoal = findViewById(R.id.KBSTextViewSoal);
        TextView KBSTextViewJudul = findViewById(R.id.KBSTextViewJudul);
        ImageButton KBSButtonBack = findViewById(R.id.KBSButtonBack);
        ImageButton KBSButtonBenar = findViewById(R.id.KBSButtonBenar);
        ImageButton KBSButtonSalah = findViewById(R.id.KBSButtonSalah);

        Intent intent = getIntent();
        Context context = getApplicationContext();
        DBHandler dbHandler = new DBHandler(context);

        String levelSoal = intent.getStringExtra("levelSoal");
        String tipeSoal = intent.getStringExtra("tipeSoal");
        String judulSoal = intent.getStringExtra("judulSoal");
        String judul = null;

        Cursor cursorKonten = null;
        int countKonten = 0;
//        String TAG = "CheckerKBS";

        boolean tipeSoalMateri = tipeSoal.equals("Materi Aqidah") || tipeSoal.equals("Materi Fiqh") || tipeSoal.equals("Materi Siroh");
        if (tipeSoal.equals("Benar Salah")) {
            String condition = Table.Soal.COL_TIPE + "='" + tipeSoal + judulSoal
                    + "' AND " + Table.Soal.COL_LEVEL_SOAL + "='" + levelSoal.replace("Level ", "") + "'";
            cursorKonten = dbHandler
                    .getData(Table.Soal.TABLE_NAME, "*", condition);

            Log.d(TAG, "masuk ke #1 if" + cursorKonten);
            Log.d(TAG, "masuk ke #1.5 if. condition: " + condition);
            judul = "Kuis " + tipeSoal + " " + judulSoal;
            //dari database COL_TIPE Soal: BSAqidah BSFiqh BSSiroh
            //tipe soal: Benar Salah
            //judul soal: Materi Aqidah, Materi Fiqh, Materi Siroh

            //dari database COL_TIPE Materi: Aqidah, Fiqh, Siroh
            //tipe soal Materi: Materi Aqidah, Materi Fiqh, Materi Siroh
        } else if (tipeSoalMateri) {
            cursorKonten = dbHandler
                    .getData(Table.Materi.TABLE_NAME, "*",
                            Table.Materi.COL_TIPE + "='" + tipeSoal + judulSoal
                                    + "' AND " + Table.Materi.COL_JUDUL + "='" + levelSoal.replace("Level ", "") + "'");
            Log.d(TAG, "masuk ke #2 else if" + cursorKonten);
            judul = tipeSoal + " " + judulSoal;
        }
        KBSTextViewJudul.setText(judul);

        List<List<String>> listOfLists = null;
        if (cursorKonten != null) {
            listOfLists = new ArrayList<>(cursorKonten.getCount());
            Log.d(TAG, "masuk ke #2.5" + cursorKonten.getCount());
        }
        if (cursorKonten != null && cursorKonten.moveToFirst()) {
            Log.d(TAG, "masuk ke #3 if");
            for (int i = 0; i < cursorKonten.getCount(); i++) {
                listOfLists.add(new ArrayList<>());
                Log.d(TAG, "masuk ke #4 for" + listOfLists.toString());
            }
            do {
                Log.d(TAG, "masuk ke #5 do");
                if (tipeSoal.equals("Benar Salah")) {
                    Log.d(TAG, "masuk ke #6 if" + tipeSoal);
                    listOfLists.get(countKonten).add(String.valueOf(cursorKonten.getString(1))); //soal
                    listOfLists.get(countKonten).add(String.valueOf(cursorKonten.getString(3))); //jawaban
                    countKonten++;
                } else {
                    Log.d(TAG, "masuk ke #7 else" + tipeSoal);
                    listOfLists.get(countKonten).add(String.valueOf(cursorKonten.getString(3))); //kontenMateri
                    countKonten++;
                }
            } while (cursorKonten.moveToNext());
        }
        Log.d(TAG, "masuk ke #8 countKonten" + countKonten);
        Integer[] randomCountKonten = new Integer[countKonten];
        for (int i = 0; i < randomCountKonten.length; i++) {
            randomCountKonten[i] = i;
        }

        if (tipeSoal.equals("Benar Salah")) {
            try {
                Collections.shuffle(Arrays.asList(randomCountKonten));
                Log.d(TAG, "masuk ke #9 try");
            } catch (Exception e) {
                Log.d(TAG, "masuk ke #10 catch");
            }
            Log.d(TAG, "masuk ke #11. randomCountKonten: " + Arrays.toString(randomCountKonten));

            AtomicInteger pageKBS = new AtomicInteger();
            pageKBS.getAndIncrement();
            AtomicInteger progressBenar = new AtomicInteger();
            AtomicInteger progressSalah = new AtomicInteger();
//        for (int i = 0; i < countKonten; i++) {
//            Log.d(TAG, "pageKBS: " + pageKBS + " progressBenar: " + progressBenar + " progressSalah: " + progressSalah);
            if (pageKBS.get() == 1) { //soal pertama
                int finalPageKBS = pageKBS.get();
                String stringPageKBS = finalPageKBS + ". ";
                KBSTextViewNomorSoal.setText(stringPageKBS);
                if (listOfLists != null) {
                    KBSTextViewSoal.setText(listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(0)); //soal
                }
            }
//            } else if (pageKBS.get() != (countKonten - 1)) {
            int finalCountKonten = countKonten;
            List<List<String>> finalListOfLists = listOfLists;
            KBSButtonBenar.setOnClickListener(v -> {
//                    String benar = "Benar";
//                    int finalPageKBS = pageKBS.get();
//                    String stringPageKBS = finalPageKBS + ". ";
//                    KBSTextViewNomorSoal.setText(stringPageKBS);
//                    KBSTextViewSoal.setText(listOfLists.get(randomCountKonten[finalPageKBS]).get(0)); //soal
//                    if (listOfLists.get(randomCountKonten[finalPageKBS]).get(1).equals(benar))
//                        progressBenar.getAndIncrement();
//                    else progressSalah.getAndIncrement();
//                    pageKBS.getAndIncrement();
                String koreksi = "Benar";
                soalSelanjutnya(pageKBS, finalCountKonten, KBSTextViewNomorSoal, KBSTextViewSoal, finalListOfLists, randomCountKonten, koreksi, progressBenar, progressSalah, tipeSoal, dbHandler, levelSoal, judulSoal);
            });
            KBSButtonSalah.setOnClickListener(v -> {
//                    String salah = "Salah";
//                    int finalPageKBS = pageKBS.get();
//                    String stringPageKBS = finalPageKBS + ". ";
//                    KBSTextViewNomorSoal.setText(stringPageKBS);
//                    KBSTextViewSoal.setText(listOfLists.get(randomCountKonten[finalPageKBS]).get(0)); //soal
//                    if (listOfLists.get(randomCountKonten[finalPageKBS]).get(1).equals(salah))
//                        progressBenar.getAndIncrement();
//                    else progressSalah.getAndIncrement();
//                    pageKBS.getAndIncrement();
                String koreksi = "Salah";
                soalSelanjutnya(pageKBS, finalCountKonten, KBSTextViewNomorSoal, KBSTextViewSoal, finalListOfLists, randomCountKonten, koreksi, progressBenar, progressSalah, tipeSoal, dbHandler, levelSoal, judulSoal);
            });
//            } else {
//                if (tipeSoal.equals("Benar Salah")) {
//                    Intent kembali = new Intent(getApplicationContext(), BenarSalahFragment.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    startActivity(kembali);
//                } else if (tipeSoal.equals("Materi Aqidah") || tipeSoal.equals("Materi Fiqh") || tipeSoal.equals("Materi Siroh")) {
//                    Intent kembali = new Intent(getApplicationContext(), MateriFragment.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    startActivity(kembali);
//                }
//            }
//        }


//        if (cursorKonten != null && cursorKonten.moveToFirst()) {
//            List<List<String>> listOfLists = new ArrayList<List<String>>(cursorKonten.getCount());
//            for (int i = 0; i < cursorKonten.getCount(); i++) {
//                listOfLists.add(new ArrayList<String>());
//            }
//            int count = 0;
//            do {
//                listOfLists.get(count).add(String.valueOf(cursorKonten.getString(0)));
//                count++;
//            } while (cursorKonten.moveToNext());
//        }

//        KBSTextViewSoal
        } else if (tipeSoalMateri) {
            //setting color dan icon button buat materi
            KBSButtonBenar.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.biru_figma)));
            KBSButtonSalah.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.biru_figma)));
            KBSButtonBenar.setImageResource(R.drawable.ic_play_arrow_backward);
            KBSButtonSalah.setImageResource(R.drawable.ic_play_arrow);
//            KBSButtonSalah.setBackgroundColor(R.drawable.bg_roundedcorner_blue);

            AtomicInteger pageKBS = new AtomicInteger();
            pageKBS.getAndIncrement();

            if (pageKBS.get() == 1) { //materi pertama buat initialize
                int finalPageKBS = pageKBS.get();
                String stringPageKBS = finalPageKBS + ". ";
                KBSTextViewNomorSoal.setText(stringPageKBS);
                if (listOfLists != null) {
                    KBSTextViewSoal.setText(listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(0)); //soal
                }
                KBSButtonBenar.setEnabled(false);
                KBSButtonSalah.setEnabled(true);
            }
            int finalCountKonten = countKonten;
            List<List<String>> finalListOfLists = listOfLists;
            KBSButtonBenar.setOnClickListener(v -> {
                pageKBS.decrementAndGet();
                materiSelanjutnya(pageKBS, finalCountKonten, KBSTextViewNomorSoal, KBSTextViewSoal, finalListOfLists, randomCountKonten, tipeSoal, dbHandler, levelSoal, judulSoal, KBSButtonBenar, KBSButtonSalah);
            });
            KBSButtonSalah.setOnClickListener(v -> {
                pageKBS.incrementAndGet();
                materiSelanjutnya(pageKBS, finalCountKonten, KBSTextViewNomorSoal, KBSTextViewSoal, finalListOfLists, randomCountKonten, tipeSoal, dbHandler, levelSoal, judulSoal, KBSButtonBenar, KBSButtonSalah);
            });
        }
        KBSButtonBack.setOnClickListener(v -> {
            String tipeSoalKembali;
            if (tipeSoal.contains("Materi")) tipeSoalKembali = "Materi";
            else tipeSoalKembali = tipeSoal;
            Intent kembali = new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            kembali.putExtra("fragmentPilihan", tipeSoalKembali);
            startActivity(kembali);
            finish();
        });
    }

//    public String trimStringDatabase(String tipeSoal, String judulSoal) {
//        String trimmedStringDatabase = null;
//        if (tipeSoal.equals("Benar Salah")) {
//            switch (judulSoal) {
//                case "Materi Aqidah":
//                    trimmedStringDatabase = "BSAqidah";
//                    return trimmedStringDatabase;
//                case "Materi Fiqh":
//                    trimmedStringDatabase = "BSFiqh";
//                    return trimmedStringDatabase;
//                case "Materi Siroh":
//                    trimmedStringDatabase = "BSSiroh";
//                    return trimmedStringDatabase;
//            }
//        }
//        switch (tipeSoal) {
//            case "Materi Aqidah":
//                trimmedStringDatabase = "Aqidah";
//                return trimmedStringDatabase;
//            case "Materi Fiqh":
//                trimmedStringDatabase = "Fiqh";
//                return trimmedStringDatabase;
//            case "Materi Siroh":
//                trimmedStringDatabase = "Siroh";
//                return trimmedStringDatabase;
//        }
//        return null;
//        //dari database COL_TIPE Soal: BSAqidah BSFiqh BSSiroh
//        //tipe soal: Benar Salah
//        //judul soal: Materi Aqidah, Materi Fiqh, Materi Siroh
//
//        //dari database COL_TIPE Materi: Aqidah, Fiqh, Siroh
//        //tipe soal Materi: Materi Aqidah, Materi Fiqh, Materi Siroh
//    }

    //    public int trimLevelSoal (String levelSoal){
//
//        return 0;
//    }
    protected void soalSelanjutnya(AtomicInteger pageKBS, int countKonten, TextView KBSTextViewNomorSoal, TextView KBSTextViewSoal, List<List<String>> listOfLists
            , Integer[] randomCountKonten, String koreksi, AtomicInteger progressBenar, AtomicInteger progressSalah, String tipeSoal, DBHandler dbHandler, String levelSoal, String judulSoal) {
        int finalPageKBS = pageKBS.get();

        if (finalPageKBS < countKonten) { //kalau
            finalPageKBS = pageKBS.incrementAndGet();
            String stringPageKBS = finalPageKBS + ". ";
            KBSTextViewNomorSoal.setText(stringPageKBS);
            KBSTextViewSoal.setText(listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(0)); //soal
            if (listOfLists.get(randomCountKonten[finalPageKBS - 2]).get(1).equals(koreksi))
                progressBenar.getAndIncrement();
            else progressSalah.getAndIncrement();
            Log.d(TAG, "#1: pageKBS: " + pageKBS + " progressBenar: " + progressBenar + " progressSalah: " + progressSalah + " jawaban dari database: " + listOfLists.get(randomCountKonten[finalPageKBS - 2]).get(1));
        } else {
            if (listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(1).equals(koreksi))
                progressBenar.getAndIncrement();
            else progressSalah.getAndIncrement();
            String tipeSoalKembali;
            if (tipeSoal.contains("Materi")) tipeSoalKembali = "Materi";
            else tipeSoalKembali = tipeSoal;

            progress(dbHandler, progressBenar, progressSalah, levelSoal, tipeSoal, judulSoal);
            Log.d(TAG, "#2: pageKBS: " + pageKBS + " progressBenar: " + progressBenar + " progressSalah: " + progressSalah + " jawaban dari database: " + listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(1));

            Intent kembali = new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            kembali.putExtra("fragmentPilihan", tipeSoalKembali);
            startActivity(kembali);
            finish();
        }
    }

    protected void materiSelanjutnya(AtomicInteger pageKBS, int countKonten, TextView KBSTextViewNomorSoal, TextView KBSTextViewSoal, List<List<String>> listOfLists
            , Integer[] randomCountKonten, String tipeSoal, DBHandler dbHandler, String levelSoal, String judulSoal, ImageButton KBSButtonBenar, ImageButton KBSButtonSalah) {
        int finalPageKBS = pageKBS.get();
        if (finalPageKBS == 1) {
            KBSButtonBenar.setEnabled(false);
            KBSButtonSalah.setEnabled(true);
        } else if (finalPageKBS < countKonten) {
            KBSButtonBenar.setEnabled(true);
            KBSButtonSalah.setEnabled(true);
        } else {
            KBSButtonBenar.setEnabled(true);
            KBSButtonSalah.setEnabled(false);
        }
        if (finalPageKBS <= countKonten) {
            String stringPageKBS = finalPageKBS + ". ";
            KBSTextViewNomorSoal.setText(stringPageKBS);
            KBSTextViewSoal.setText(listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(0)); //soal
//            Log.d(TAG, "#1: pageKBS: " + pageKBS + " progressBenar: " + progressBenar + " progressSalah: " + progressSalah + " jawaban dari database: " + listOfLists.get(randomCountKonten[finalPageKBS - 2]).get(1));
        }
        if (finalPageKBS == countKonten) {
            progress(dbHandler, null, null, levelSoal, tipeSoal, judulSoal);
//            Log.d(TAG, "#2: pageKBS: " + pageKBS + " progressBenar: " + progressBenar + " progressSalah: " + progressSalah + " jawaban dari database: " + listOfLists.get(randomCountKonten[finalPageKBS - 1]).get(1));
        }
    }

    protected void progress(DBHandler dbHandler, AtomicInteger progressBenar, AtomicInteger progressSalah, String levelSoal, String tipeSoal, String judulSoal) {
        int countKonten = 0;
        int trimmedLevelSoal = Integer.parseInt(levelSoal.replace("Level ", ""));
        String conditionStatistik = Table.Statistik.COL_TIPE + "='" + tipeSoal + judulSoal
                + "' AND " + Table.Statistik.COL_LEVEL_SOAL + "='" + trimmedLevelSoal + "'";
        String conditionProgress = Table.Progress.COL_TIPE + "='" + tipeSoal + judulSoal + "'";

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

//        Cursor cursorProgress = dbHandler.getData(Table.Progress.TABLE_NAME, "*", conditionProgress);
//        if (cursorProgress != null && cursorProgress.moveToFirst()) {
//            //update instead
//            int levelProgressDatabase = Integer.parseInt(cursorProgress.getString(1).replace("Level ", ""));
//            String stringLevelProgress = "Level " + trimmedLevelSoal;
//            Log.d(TAG, "levelProgressDatabase: " + levelProgressDatabase + " levelProgress: " + trimmedLevelSoal);
//            if (levelProgressDatabase < trimmedLevelSoal) {
//                dbHandler.updateDataProgress(String.valueOf(trimmedLevelSoal), tipeSoal + judulSoal);
//            }
//        } else {
            //insert instead
//            dbHandler.insertUpdateDataProgress(String.valueOf(1), tipeSoal, judulSoal, trimmedLevelSoal);
            Log.d(TAG, "tipeProgress: " + tipeSoal + judulSoal);
//        }


        //ngecek aja buat testing udah masuk apa belom datanya
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