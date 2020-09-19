package com.example.base.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.base.helper.ActivityLaunchHelper
import com.example.base.helper.DefaultLogin
import com.example.base.helper.isLoggedIn
import com.example.base.helper.login
import com.example.base.model.Worker

class SignInFragment : Fragment() {
    private var firstNameView: EditText? = null
    private var lastNameView: EditText? = null
    private var worker: Worker? = null
    private val edit by lazy { arguments?.getBoolean(ARG_EDIT, false) ?: false }

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.run {
            if(isLoggedIn()) {
                navigateToCategoryActivity()
            } else {
                login.loginWorker(this, ::onSuccessfulLogin)
            }
        }
        super.onCreate(savedInstanceState)
    }

    private fun navigateToCategoryActivity() {
        activity?.run {
            ActivityLaunchHelper.launchCategorySelection(this)
            supportFinishAfterTransition()
        }
    }

    /**
     * Called when logged in successfully
     */
    private fun onSuccessfulLogin(worker: Worker) {
        if(login != DefaultLogin) return
        this.worker = worker
        if(edit) {
            with(worker) {
                firstNameView?.setText(worker.firstName)
                lastNameView?.run {
                    setText(worker.lastName)
                    requestFocus()
                    setSelection(length())
                }
                this@SignInFragment.worker = worker.also {
                    if(activity != null) {
                        login.saveWorker(activity!!, this, {})
                    }
                }
            }
        } else {
            navigateToCategoryActivity()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val ARG_EDIT = "EDIT"
        fun newInstance(edit: Boolean = false): SignInFragment {
            return SignInFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_EDIT, edit)
                }
            }
        }
    }
}
