package com.gmail.elnora.fet.hw_4_customview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        onOff = loadSetting();
        switchShowMessage = findViewById(R.id.switchShowMessage);
        switchShowMessage.setChecked(onOff);

        setOnCheckedChangeListener();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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

}