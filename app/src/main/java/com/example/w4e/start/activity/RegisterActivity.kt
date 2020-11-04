package com.example.w4e.start.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.example.w4e.start.R
import com.example.w4e.start.helper.UserDatabaseHelper
import com.example.w4e.start.helper.InputValidation
import com.example.w4e.start.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.IOException

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private val activity = this@RegisterActivity
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutConfirmPassword: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textInputEditTextConfirmPassword: TextInputEditText
    private lateinit var appCompatButtonRegister: AppCompatButton
    private lateinit var appCompatTextViewLoginLink: AppCompatTextView
    private lateinit var inputValidation: InputValidation
    private lateinit var userDatabaseHelper: UserDatabaseHelper
    private lateinit var selectFileButton: Button
    private var imageData: ByteArray? = null
    private lateinit var imageView: ImageView

    companion object {
        private const val IMAGE_PICK_CODE = 999
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar!!.hide()
        initViews()
        initListeners()
        initObjects()
    }

    private fun initObjects() {
        userDatabaseHelper = UserDatabaseHelper(activity)
        inputValidation = InputValidation(activity)
    }

    private fun initListeners() {
        // set OnClickListener is appCompatButtonRegister is non-null value or throw
        // NullPointerException if appCompatButtonRegister is a null
        appCompatButtonRegister!!.setOnClickListener(this)
        appCompatTextViewLoginLink!!.setOnClickListener(this)
        selectFileButton!!.setOnClickListener(this)
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun initViews() {
        imageView = findViewById(R.id.imageView)
        selectFileButton = findViewById(R.id.selectFileButton)
        nestedScrollView = findViewById(R.id.nestedScrollView)
        textInputLayoutName = findViewById(R.id.textInputLayoutName)
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail)
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword)
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword)
        textInputEditTextName = findViewById(R.id.textInputEditTextName)
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail)
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword)
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword)
        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister)
        appCompatTextViewLoginLink = findViewById(R.id.appCompatTextViewLoginLink)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.appCompatButtonRegister -> postDataToSQLite()
            R.id.appCompatTextViewLoginLink -> finish()
            R.id.selectFileButton -> selectFile()
        }
    }

    private fun postDataToSQLite() {
        if(!inputValidation!!.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return
        }
        if(!inputValidation!!.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if(!inputValidation!!.isInputEditTextEmail(textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_email))) {
            return
        }
        if(!inputValidation!!.isInputEditTextFilled(textInputEditTextPassword!!, textInputLayoutPassword!!, getString(R.string.error_message_email))) {
            return
        }
        if(!userDatabaseHelper!!.checkUser(textInputEditTextEmail!!.text.toString().trim {it <= ' '}, textInputEditTextPassword!!.text.toString().trim {it <= ' '})) {
            var user = User(name = textInputEditTextName!!.text.toString().trim(),
            email = textInputEditTextEmail!!.text.toString().trim(),
            password = textInputEditTextPassword!!.text.toString().trim())
            userDatabaseHelper!!.addUser(user)
            // Snack Bar to show success message that record is saved successfully
            Snackbar.make(nestedScrollView!!, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
            emptyInputEditText()
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView!!, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        textInputEditTextEmail!!.text = null
        textInputEditTextPassword!!.text = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val uri = data?.data
            if (uri != null) {
                imageView.setImageURI(uri)
                createImageData(uri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }
}