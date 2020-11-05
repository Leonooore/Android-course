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
                String message = String.format(Locale.getDefault(), "Нажаты координаты [%.0f;%.0f]", eventX, eventY);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
