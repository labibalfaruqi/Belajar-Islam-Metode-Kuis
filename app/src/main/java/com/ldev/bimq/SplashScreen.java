package com.ldev.bimq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ldev.bimq.sqlite.DBHandler;
import com.ldev.bimq.sqlite.Table;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int SPLASH_DISPLAY_LENGTH = 1000;
        new Handler().postDelayed(() -> {
            DBHandler dbHandler = new DBHandler(getApplicationContext());
            dbHandler.getData(Table.Materi.TABLE_NAME, Table.Materi.COL_ID, null);
            /* Create an Intent that will start the Menu-Activity. */
            Intent intent = new Intent(SplashScreen.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}