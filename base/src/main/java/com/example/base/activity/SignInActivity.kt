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

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        if (savedInstanceState == null) {
            replaceFragment(R.id.sign_in_container,
                SignInFragment.newInstance(isInEditMode || !isLoggedIn()))
        }
    }

    override fun onStop() {
        super.onStop()
        if(isLoggedIn()) finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // forwarding all results to SignInFragment for further handling
        findFragmentById(R.id.sign_in_container)?.onActivityResult(requestCode, resultCode, data)
    }

    private val isInEditMode get() = intent.getBooleanExtra(EXTRA_EDIT, false)
}