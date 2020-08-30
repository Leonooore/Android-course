package com.gmail.elnora.fet.hw_5_contacts_database_kotlin;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class AddContactActivity extends AppCompatActivity {
    private ImageButton buttonBack;
    private ImageButton buttonAdd;
    private RadioGroup radioGroup;
    private RadioButton radioButtonPhoneNumber;
    private RadioButton radioButtonEmail;
    private EditText editTextName;
    private EditText editTextData;
    private Contact contact;
    private DataType dataType = DataType.PHONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        buttonBack = findViewById(R.id.buttonBack);
        buttonAdd = findViewById(R.id.buttonAdd);
        radioGroup = findViewById(R.id.radioButtonGroup);
        radioButtonPhoneNumber = findViewById(R.id.radioButtonPhoneNumber);
        radioButtonEmail = findViewById(R.id.radioButtonEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextData = findViewById(R.id.editTextData);

        setListeners();
    }

    private void setListeners() {
        setButtonBackListener();
        setButtonAddListener();
        setRadioGroupListener();
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
                String id = String.valueOf(UUID.randomUUID());

                if (name.isEmpty() || data.isEmpty()) {
                    String message = "Please input data into all fields!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    contact = new Contact(id, name, data, dataType);
                    Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                    intent.putExtra("ADD_CONTACT", contact);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void setRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonPhoneNumber:
                        setRadioButtonPhoneNumberListener();
                        break;
                    case R.id.radioButtonEmail:
                        setRadioButtonEmailListener();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setRadioButtonPhoneNumberListener() {
        editTextData.setHint(R.string.contactPhoneNumber);
        editTextData.setInputType(InputType.TYPE_CLASS_PHONE);
        dataType = DataType.PHONE;
    }

    private void setRadioButtonEmailListener() {
        editTextData.setHint(R.string.contactEmail);
        editTextData.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        dataType = DataType.EMAIL;
    }

}
