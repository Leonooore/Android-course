package com.gmail.elnora.fet.hw_3_contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton buttonAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtonAddContactListener();
    }

    private void setButtonAddContactListener() {
        buttonAddContact = findViewById(R.id.buttonAddContact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, 18000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 18000 && resultCode == Activity.RESULT_OK && data != null) {
            String name = data.getStringExtra("ADD_CONTACT");
            Log.d("MainActivity", "Name: " + name);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
