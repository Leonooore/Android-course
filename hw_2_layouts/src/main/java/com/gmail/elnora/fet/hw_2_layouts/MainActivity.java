package com.gmail.elnora.fet.hw_2_layouts;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import static com.gmail.elnora.fet.hw_2_layouts.R.layout.task1_linear_layout;
import static com.gmail.elnora.fet.hw_2_layouts.R.layout.task1_table_layout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(task1_linear_layout);
    }
}
