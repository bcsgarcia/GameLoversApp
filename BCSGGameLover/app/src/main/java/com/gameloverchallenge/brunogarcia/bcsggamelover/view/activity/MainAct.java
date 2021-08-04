package com.gameloverchallenge.brunogarcia.bcsggamelover.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.gameloverchallenge.brunogarcia.bcsggamelover.R;
import com.gameloverchallenge.brunogarcia.bcsggamelover.cache.CacheData;

public class MainAct extends AppCompatActivity {

    private String TAG = "MainAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        new Thread(() -> {
                CacheData.getInstance(getBaseContext()).populateListPlatforms();
                while (CacheData.getInstance(getBaseContext()).getListPlatforms().size() == 0){  }
                Intent intent = new Intent(MainAct.this, PlatformAct.class);
                startActivity(intent);
                MainAct.this.finish();
        }).start();
    }
}
