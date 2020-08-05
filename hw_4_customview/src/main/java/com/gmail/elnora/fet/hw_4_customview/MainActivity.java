package com.gmail.elnora.fet.hw_4_customview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customView = findViewById(R.id.customView);
        customView.setOnTouchEventListener(new CustomView.OnTouchEventListener() {
            @Override
            public void onTouchDown(int x, int y) {

            }
        });
    }
}
