package com.example.work4experience

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.work4experience.helper.replaceFragment

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in)
        if(savedInstanceState == null) {

        }
    }
}