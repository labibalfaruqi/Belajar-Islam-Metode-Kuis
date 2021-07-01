package com.ldev.bimq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class PemilihanLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemilihan_level);

        String TAG = "CheckerPL";

        ImageButton PLButtonBack = findViewById(R.id.PLButtonBack);
        TextView PLTextViewJudul = findViewById(R.id.PLTextViewJudul);
        Button PLButtonLevelSatu = findViewById(R.id.PLButtonLevelSatu);
        Button PLButtonLevelDua = findViewById(R.id.PLButtonLevelDua);
        Button PLButtonLevelTiga = findViewById(R.id.PLButtonLevelTiga);
        Button PLButtonLevelEmpat = findViewById(R.id.PLButtonLevelEmpat);
        Button PLButtonLevelLima = findViewById(R.id.PLButtonLevelLima);
//        Button PLButtonLevelEnam = findViewById(R.id.PLButtonLevelEnam);
        Intent intent = getIntent();
        String tipeSoal = intent.getStringExtra("tipeSoal");
        String judulSoal = intent.getStringExtra("judulSoal");
        String levelSoal = intent.getStringExtra("levelSoal");
        int integerLevelSoal = Integer.parseInt(levelSoal.replace("Level ", ""));
        String PLTextViewJudulString = tipeSoal + "\n" + judulSoal;
        PLButtonLevelSatu.setEnabled(false);
        PLButtonLevelDua.setEnabled(false);
        PLButtonLevelTiga.setEnabled(false);
        PLButtonLevelEmpat.setEnabled(false);
        PLButtonLevelLima.setEnabled(false);
        Log.d(TAG, "integerLevelSoal: " + integerLevelSoal);
        Log.d(TAG, "levelSoal: " + levelSoal);
        if (integerLevelSoal != 0) {
            Log.d(TAG, "masuk if integerLevelSoal != 0");
            if (1 <= integerLevelSoal) enableButton(PLButtonLevelSatu);
            if (2 <= integerLevelSoal) enableButton(PLButtonLevelDua);
            if (3 <= integerLevelSoal) enableButton(PLButtonLevelTiga);
            if (4 <= integerLevelSoal) enableButton(PLButtonLevelEmpat);
            if (5 <= integerLevelSoal) enableButton(PLButtonLevelLima);
//            if (6 <= integerLevelSoal) {
//                PLButtonLevelEnam.setVisibility(View.VISIBLE);
//                enableButton(PLButtonLevelEnam);
//            }

        }
        PLTextViewJudul.setText(PLTextViewJudulString);

        PLButtonBack.setOnClickListener(v -> {
            onBackPressed();
        });
        PLButtonLevelSatu.setOnClickListener(v -> {
            pindahHalamanLevel(1, tipeSoal, judulSoal);
        });
        PLButtonLevelDua.setOnClickListener(v -> {
            pindahHalamanLevel(2, tipeSoal, judulSoal);
        });
        PLButtonLevelTiga.setOnClickListener(v -> {
            pindahHalamanLevel(3, tipeSoal, judulSoal);
        });
        PLButtonLevelEmpat.setOnClickListener(v -> {
            pindahHalamanLevel(4, tipeSoal, judulSoal);
        });
        PLButtonLevelLima.setOnClickListener(v -> {
            pindahHalamanLevel(5, tipeSoal, judulSoal);
        });
    }

    protected void pindahHalamanLevel(int level, String tipeSoal, String judulSoal) {
        String stringLevel = "Level " + level;
        Intent intentPindahHalaman = new Intent(getApplicationContext(), StartPlayActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intentPindahHalaman.putExtra("tipeSoal", tipeSoal);
        intentPindahHalaman.putExtra("judulSoal", judulSoal);
        intentPindahHalaman.putExtra("levelSoal", stringLevel);
        startActivity(intentPindahHalaman);
        finish();
    }

    protected void enableButton(Button button) {
        button.setClickable(true);
        button.setEnabled(true);
        button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_roundedcorner_blue));
        button.setBackgroundResource(R.drawable.bg_roundedcorner_blue);
//        button.setImageResource(R.drawable.bg_roundedcorner_blue);
    }
}