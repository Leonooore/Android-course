package com.gmail.elnora.fet.hw_5_contacts_database_kotlin

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.data.DataType
import com.gmail.elnora.fet.hw_5_contacts_database_kotlin.database.Contact
import kotlinx.android.synthetic.main.activity_add_contact.buttonBack
import kotlinx.android.synthetic.main.activity_add_contact.buttonAdd
import kotlinx.android.synthetic.main.activity_add_contact.editTextName
import kotlinx.android.synthetic.main.activity_add_contact.editTextData
import kotlinx.android.synthetic.main.activity_add_contact.radioButtonGroup
import java.util.UUID

class AddContactActivity : AppCompatActivity() {
    private lateinit var contact: Contact
    private var dataType = DataType.PHONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        setListeners()
    }

    private fun setListeners() {
        setButtonBackListener()
        setButtonAddListener()
        setRadioGroupListener()
    }

    private fun setButtonBackListener() {
        buttonBack.setOnClickListener { finish() }
    }

    private fun setButtonAddListener() {
        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val data = editTextData.text.toString()
            val id = UUID.randomUUID().toString()
            if (name.isEmpty() || data.isEmpty()) {
                val message = "Please input data into all fields!"
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            } else {
                contact = Contact(id, name, data, dataType)
                val intent = Intent(this@AddContactActivity, MainActivity::class.java)
                intent.putExtra("ADD_CONTACT", contact)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun setRadioGroupListener() {
        radioButtonGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonPhoneNumber -> setRadioButtonPhoneNumberListener()
                R.id.radioButtonEmail -> setRadioButtonEmailListener()
            }
        }
    }

    private fun setRadioButtonPhoneNumberListener() {
        editTextData.setHint(R.string.contactPhoneNumber)
        editTextData.inputType = InputType.TYPE_CLASS_PHONE
        dataType = DataType.PHONE
    }

    private fun setRadioButtonEmailListener() {
        editTextData.setHint(R.string.contactEmail)
        editTextData.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        dataType = DataType.EMAIL
    }
}