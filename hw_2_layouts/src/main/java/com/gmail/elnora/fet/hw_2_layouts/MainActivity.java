package com.gmail.elnora.fet.hw_2_layouts;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.task6_custom_view);
        setContentView(new CustomView(this));

        //CustomView customView = findViewById(R.id.colorCircle);

    }

}
