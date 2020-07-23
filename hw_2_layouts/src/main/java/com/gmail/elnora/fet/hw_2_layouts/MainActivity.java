package com.gmail.elnora.fet.hw_2_layouts;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task6_custom_view);

        CustomView customView = findViewById(R.id.colorCircle);
        customView.setListener(new CustomView.OnTouchEvent() {
            @Override
            public void onTouchEvent(float eventX, float eventY) {

            }
        });
    }

}
