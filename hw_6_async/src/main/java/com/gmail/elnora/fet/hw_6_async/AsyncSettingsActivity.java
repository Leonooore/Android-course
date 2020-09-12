package com.gmail.elnora.fet.hw_6_async;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AsyncSettingsActivity extends AppCompatActivity {
    private ImageButton buttonBack;
    private RadioGroup radioGroupSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_settings);

        initViews();
        setRadioGroupListener();
        setButtonBackListener();
    }

    private void initViews() {
        radioGroupSettings = findViewById(R.id.radioGroupSettings);
        buttonBack = findViewById(R.id.buttonBack);
    }

    private void setRadioGroupListener() {
        radioGroupSettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonThreadPoolExecutorHandler) {

                } else if (checkedId == R.id.radioButtonCompletableFutureThreadPoolExecutor) {

                } else if (checkedId == R.id.radioButtonRxJava) {

                }
            }
        });
    }

    private void setButtonBackListener() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
