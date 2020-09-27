package com.gmail.elnora.fet.hw_6_async;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AsyncSettingsActivity extends AppCompatActivity {
    private static final String THREADPOOLEXECUTER_HANDLER = "THREADPOOLEXECUTER_HANDLER";
    private static final String COMPLETABLEFUTURE_THREADPOOLEXECUTOR = "COMPLETABLEFUTURE_THREADPOOLEXECUTOR";
    private static final String RXJAVA = "RXJAVA";
    private static final String PREF_SAVE_KEY = "ASYNC_TYPE_KEY";
    private static final String PREF_NAME = "asyncTypePref";
    private SharedPreferences sharedPreferences;
    private ImageButton buttonBack;
    private RadioGroup radioGroupSettings;
    private RadioButton radioButtonThreadPoolExecutorHandler;
    private RadioButton radioButtonCompletableFutureThreadPoolExecutor;
    private RadioButton radioButtonRxJava;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_settings);
        initViews();
        loadPrefSettings();
        setRadioGroupListener();
        setButtonBackListener();
    }

    private void initViews() {
        radioGroupSettings = findViewById(R.id.radioGroupSettings);
        radioButtonThreadPoolExecutorHandler = findViewById(R.id.radioButtonThreadPoolExecutorHandler);
        radioButtonCompletableFutureThreadPoolExecutor = findViewById(R.id.radioButtonCompletableFutureThreadPoolExecutor);
        radioButtonRxJava = findViewById(R.id.radioButtonRxJava);
        buttonBack = findViewById(R.id.buttonBack);
    }

    private void loadPrefSettings() {
        String loadAsyncType = loadAsyncType();
        if (THREADPOOLEXECUTER_HANDLER.equals(loadAsyncType)) {
            radioButtonThreadPoolExecutorHandler.setChecked(true);
        } else if (COMPLETABLEFUTURE_THREADPOOLEXECUTOR.equals(loadAsyncType)) {
            radioButtonCompletableFutureThreadPoolExecutor.setChecked(true);
        } else if (RXJAVA.equals(loadAsyncType)) {
            radioButtonRxJava.setChecked(true);
        } else {
            throw new IllegalStateException("Unexpected value: " + loadAsyncType);
        }
    }

    private void setRadioGroupListener() {
        radioGroupSettings.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonThreadPoolExecutorHandler) {
                    saveStorageAsyncType(THREADPOOLEXECUTER_HANDLER);
                } else if (checkedId == R.id.radioButtonCompletableFutureThreadPoolExecutor) {
                    saveStorageAsyncType(COMPLETABLEFUTURE_THREADPOOLEXECUTOR);
                } else if (checkedId == R.id.radioButtonRxJava) {
                    saveStorageAsyncType(RXJAVA);
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

    private void saveStorageAsyncType(String asyncType) {
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Editor edit = sharedPreferences.edit();
        edit.putString(PREF_SAVE_KEY, asyncType);
        edit.apply();
    }

    private String loadAsyncType() {
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(PREF_SAVE_KEY, THREADPOOLEXECUTER_HANDLER);
    }

    public static String getPrefSaveKey() {
        return PREF_SAVE_KEY;
    }

    public static String getPrefName() {
        return PREF_NAME;
    }

}
