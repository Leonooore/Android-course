package com.gmail.elnora.fet.hw_4_customview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_SAVE_KEY = "TOAST_SNACK";
    private static final String PREF_NAME = "PrefSettingActivity";
    private CustomView customView;
    private boolean settingChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingChecked = loadSetting();

        customView = findViewById(R.id.customView);
        setOnTouchEventListener();
    }

    private void setOnTouchEventListener() {
        customView.setOnTouchEventListener(new CustomView.OnTouchEventListener() {
            @Override
            public void onTouchDown(int x, int y) {
                String message = String.format(Locale.getDefault(), "Нажаты координаты [%d;%d]", x, y);
                if (settingChecked != false) {
                    Snackbar.make(findViewById(R.id.parentLayout), message, Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.itemSettingShowMessage) {
            startSettingActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSettingActivity() {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    private boolean loadSetting() {
        SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return shared.getBoolean(PREF_SAVE_KEY, false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        settingChecked = loadSetting();
    }

}
