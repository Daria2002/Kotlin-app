package com.example.w4e.start.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.activity_category.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val activity = this@LoginActivity
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var appCompatButtonLogin: AppCompatButton
    private lateinit var textViewLinkRegister: AppCompatTextView
    private lateinit var inputValidation: InputValidation
    private lateinit var user: User
    private lateinit var userDatabaseHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()
        initViews()
        initListeners()
        initObjects()
    }

    /**
     * This method initialize views
     */
    private fun initViews() {
        nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
        textInputLayoutEmail = findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
        textInputLayoutPassword = findViewById<TextInputLayout>(R.id.textInputLayoutPassword)
        textInputEditTextEmail = findViewById<TextInputEditText>(R.id.textInputEditTextEmail)
        textInputEditTextPassword = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)
        appCompatButtonLogin = findViewById<AppCompatButton>(R.id.appCompatButtonLogin)
        textViewLinkRegister = findViewById<AppCompatTextView>(R.id.textViewLinkRegister)
    }

    /**
     * This method initialize listeners
     */
    private fun initListeners() {
        appCompatButtonLogin.setOnClickListener(this)
        textViewLinkRegister.setOnClickListener(this)
    }

    /**
     * This method initialize objects
     */
    private fun initObjects() {
        userDatabaseHelper = UserDatabaseHelper(activity)
        inputValidation = InputValidation(activity)
    }

    /**
     * This method listens the click on view
     */
    override fun onClick(v: View) {
        when(v.id) {
            R.id.appCompatButtonLogin -> verifyFromSQLite()
            R.id.textViewLinkRegister -> {
                // Navigate to RegisterActivity
                val intentRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }

    /**
     * This method validates the input text fields and verify login credentials from SQLite
     */
    private fun verifyFromSQLite() {
        if(!inputValidation.isInputEditTextFilled(textInputEditTextEmail,
                textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if(!inputValidation.isInputEditTextEmail(textInputEditTextEmail,
                textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextPassword,
                textInputLayoutPassword, getString(R.string.error_message_email))) {
            return
        }
        if(userDatabaseHelper.checkUser(textInputEditTextEmail.text.toString().trim {it <= ' '},
                textInputEditTextPassword.text.toString().trim{ it <= ' '})) {
            val accountsIntent = Intent(activity, CategorySelectionActivity::class.java)
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.text.toString().trim {it <= ' '})
            emptyInputEditText()
            startActivity(accountsIntent)
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * This method clears all input edit text
     */
    private fun emptyInputEditText() {
        textInputEditTextEmail.text = null
        textInputEditTextPassword.text = null
    }
}