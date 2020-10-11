package com.gmail.elnora.fet.finalcourseproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gmail.elnora.fet.finalcourseproject.R;
import com.gmail.elnora.fet.finalcourseproject.data.dataconverter.JokeDataModelConverter;
import com.gmail.elnora.fet.finalcourseproject.repo.RecipesRepositoryImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;

public class WelcomeActivity extends AppCompatActivity {

    private Button viewButtonWelcome;
    private TextView viewTextFoodJokes;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private JokeDataModelConverter jokeDataModelConverter = new JokeDataModelConverter();
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        initViews();
        buttonClickListener();
        initJoke();
    }

    private void initViews() {
        viewButtonWelcome = findViewById(R.id.viewButtonWelcome);
        viewTextFoodJokes = findViewById(R.id.viewTextFoodJokes);
    }

    private void buttonClickListener() {
        viewButtonWelcome.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void initJoke() {
        disposable = new RecipesRepositoryImpl(okHttpClient, jokeDataModelConverter).getJoke()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(joke -> viewTextFoodJokes.setText(joke.getJoke()),
                        throwable -> Log.d("WELCOME_JOKE", throwable.toString()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
