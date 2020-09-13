package com.gmail.elnora.fet.hw_5_contacts_database_kotlin

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.data.DataType
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.database.Contact
import kotlinx.android.synthetic.main.activity_edit_contact.editTextData
import kotlinx.android.synthetic.main.activity_edit_contact.editTextName
import kotlinx.android.synthetic.main.activity_edit_contact.buttonBack
import kotlinx.android.synthetic.main.activity_edit_contact.buttonRemove

class EditContactActivity : AppCompatActivity() {
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        getContactIntent()
        setContactData()
        setButtonBackListener()
        setButtonRemoveContactListener()
    }

    private fun getContactIntent() {
        val intent = intent
        contact = intent.getSerializableExtra("EDIT_CONTACT") as Contact
    }

    private fun setContactData() {
        if (contact.dataType === DataType.PHONE) {
            editTextData.setHint(R.string.contactPhoneNumber)
            editTextData.inputType = InputType.TYPE_CLASS_PHONE
        } else {
            editTextData.setHint(R.string.contactEmail)
            editTextData.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        editTextName.setText(contact.name)
        editTextData.setText(contact.data)
    }

    private fun setButtonBackListener() {
        buttonBack.setOnClickListener {
            val name = editTextName.text.toString()
            val data = editTextData.text.toString()
            val changedContact: Contact
            changedContact = if (contact.dataType === DataType.PHONE) {
                Contact(contact.id, name, data, DataType.PHONE)
            } else {
                Contact(contact.id, name, data, DataType.EMAIL)
            }
            val intent = Intent(this@EditContactActivity, MainActivity::class.java)
            intent.putExtra("CHANGE_CONTACT", changedContact)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun setButtonRemoveContactListener() {
        buttonRemove.setOnClickListener {
            val intent = Intent(this@EditContactActivity, MainActivity::class.java)
            intent.putExtra("REMOVE_CONTACT", contact)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}