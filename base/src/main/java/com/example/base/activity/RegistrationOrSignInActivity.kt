package com.example.base.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.base.R
import com.example.base.fragment.SignInFragment
import com.example.base.helper.replaceFragment
import com.example.base.helper.isLoggedIn
import com.example.base.helper.ActivityLaunchHelper.Companion.EXTRA_EDIT
import com.example.base.helper.findFragmentById

class RegistrationOrSignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_or_sign_in)
        if (savedInstanceState == null) {
            replaceFragment(R.id.fragment_registration_or_sign_in_content,
                SignInFragment.newInstance(isInEditMode || !isLoggedIn()))
        }
    }

    override fun onStop() {
        super.onStop()
        if(isLoggedIn()) finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // forwarding all results to SignInFragment for further handling
        findFragmentById(R.id.fragment_registration_or_sign_in_content)?.onActivityResult(requestCode, resultCode, data)
    }

    private val isInEditMode get() = intent.getBooleanExtra(EXTRA_EDIT, false)
}