package com.example.w4e.start.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.example.w4e.start.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val activity = this@LoginActivity
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var appCompatButtonLogin: AppCompatButton
    private lateinit var textViewLinkRegister: AppCompatTextView
    /*
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
        /*
        appCompatButtonLogin = findViewById<AppCompatButton>(R.id.appCompatButtonRegister)
        textViewLinkRegister = findViewById<AppCompatTextView>(R.id.textViewLinkRegister)
        */
    }

    /**
     * This method initialize listeners
     */
    private fun initListeners() {
        /*
        appCompatButtonLogin.setOnClickListener(this)
        textViewLinkRegister.setOnClickListener(this)
         */
    }

    /**
     * This method initialize objects
     */
    private fun initObjects() {

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

    private fun verifyFromSQLite() {
        TODO("Not yet implemented")
    }
}