package com.gmail.elnora.fet.hw_4_customview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingActivity extends AppCompatActivity {
    private static final String PREF_SAVE_KEY = "TOAST_SNACK";
    private static final String PREF_NAME = "PrefSettingActivity";
    private Switch switchShowMessage;
    private ImageButton buttonBack;
    private boolean onOff = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonBack = findViewById(R.id.buttonBack);
        setButtonBackListener();

        switchShowMessage = findViewById(R.id.switchShowMessage);

        onOff = loadSetting();
        switchShowMessage.setChecked(onOff);

        setOnCheckedChangeListener();

    }

    private void setOnCheckedChangeListener() {
        switchShowMessage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                onOff = isChecked;
                saveSetting(onOff);
            }
        });
    }

    private void saveSetting(Boolean isChecked) {
        SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.putBoolean(PREF_SAVE_KEY, isChecked);
        edit.apply();
    }

    private boolean loadSetting() {
        SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return shared.getBoolean(PREF_SAVE_KEY, false);
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