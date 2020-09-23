package com.example.base.fragment

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.base.R
import com.example.base.helper.*
import com.example.base.model.Worker
import com.example.base.widget.TextWatcherAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RegistrationOrSignInFragment : Fragment() {
    private var findAJob: FloatingActionButton? = null
    private var findAnEmployee: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        findAJob?.show()
        findAnEmployee?.show()
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        findAJob = view.findViewById<FloatingActionButton>(R.id.find_a_job)
        findAnEmployee = view.findViewById<FloatingActionButton>(R.id.find_an_employee)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {
        val contentView = inflater.inflate(R.layout.fragment_registration_or_sign_in, container, false)
        return contentView
    }
}