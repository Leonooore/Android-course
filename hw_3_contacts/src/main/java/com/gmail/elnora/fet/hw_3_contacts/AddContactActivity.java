package com.gmail.elnora.fet.hw_3_contacts;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    ImageButton buttonBack;
    ImageButton buttonAdd;
    RadioButton radioButtonPhoneNumber;
    RadioButton radioButtonEmail;
    EditText editTextName;
    EditText editTextData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        buttonBack = findViewById(R.id.buttonBack);
        buttonAdd = findViewById(R.id.buttonAdd);
        radioButtonPhoneNumber = findViewById(R.id.radioButtonPhoneNumber);
        radioButtonEmail = findViewById(R.id.radioButtonEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextData= findViewById(R.id.editTextData);

        setListeners();
    }

    private void setListeners() {
        setButtonBackListener();
        setButtonAddListener();
        setRadioButtonPhoneNumberListener();
        setRadioButtonEmailListener();
    }

    private void setButtonBackListener() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setButtonAddListener() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String data = editTextData.getText().toString();
                Intent result = new Intent(AddContactActivity.this, MainActivity.class);
                result.putExtra("ADD_CONTACT", name);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    private void setRadioButtonPhoneNumberListener() {
        radioButtonPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextData.setHint(R.string.contactPhoneNumber);
                editTextData.setInputType(InputType.TYPE_CLASS_PHONE);
            }
        });
    }

    private void setRadioButtonEmailListener() {
        radioButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextData.setHint(R.string.contactEmail);
                editTextData.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
        });
    }

}
