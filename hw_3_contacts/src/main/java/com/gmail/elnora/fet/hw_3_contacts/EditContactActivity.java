package com.gmail.elnora.fet.hw_3_contacts;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditContactActivity extends AppCompatActivity {
    private ImageButton buttonBack;
    private EditText editTextName;
    private EditText editTextData;
    private Contact contact;
    private Button buttonRemoveContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        editTextName = findViewById(R.id.editTextName);
        editTextData =  findViewById(R.id.editTextData);
        buttonBack = findViewById(R.id.buttonBack);
        buttonRemoveContact = findViewById(R.id.buttonRemove);

        getContactIntent();
        setContactData();
        setButtonBackListener();
        setButtonRemoveContactListener();
    }

    private void getContactIntent() {
        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra("EDIT_CONTACT");
    }

    private void setContactData() {
        if (contact.getDataType() == DataType.PHONE) {
            editTextData.setHint(R.string.contactPhoneNumber);
            editTextData.setInputType(InputType.TYPE_CLASS_PHONE);
        } else {
            editTextData.setHint(R.string.contactEmail);
            editTextData.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
        editTextName.setText(contact.getName());
        editTextData.setText(contact.getData());
    }

    private void setButtonBackListener() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String data = editTextData.getText().toString();
                Contact changedContact;
                if (contact.getDataType() == DataType.PHONE) {
                    changedContact = new Contact(contact.getId(), name, data, DataType.PHONE);
                } else {
                    changedContact = new Contact(contact.getId(), name, data, DataType.EMAIL);
                }
                Intent intent = new Intent(EditContactActivity.this, MainActivity.class);
                intent.putExtra("CHANGE_CONTACT", changedContact);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setButtonRemoveContactListener() {
        buttonRemoveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditContactActivity.this, MainActivity.class);
                intent.putExtra("REMOVE_CONTACT", contact);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
