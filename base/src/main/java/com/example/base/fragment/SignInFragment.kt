package com.example.base.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.base.R
import com.example.base.helper.*
import com.example.base.model.Worker
import com.example.base.widget.TextWatcherAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SignInFragment : Fragment() {
    private var firstNameView: EditText? = null
    private var lastNameView: EditText? = null
    private var worker: Worker? = null
    private var doneFab: FloatingActionButton? = null
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
        val contentView = inflater.inflate(R.layout.fragment_sign_in, container, false)
        contentView.onLayoutChange {
            // TODO select image
        }
        return  contentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firstNameView = view.findViewById<EditText>(R.id.first_name)
        lastNameView = view.findViewById<EditText>(R.id.last_name)
        doneFab = view.findViewById<FloatingActionButton>(R.id.done)

        if(edit || (worker != null && worker!!.valid())) {
            initContentViews()
            initContents()
        }
        hideEmptyView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun hideEmptyView() {
        view?.run {
            findViewById<View>(R.id.empty).visibility = View.GONE
            findViewById<View>(R.id.content).visibility = View.VISIBLE
        }
    }

    private fun initContents() {
        worker?.run {
            valid().let {
                firstNameView?.setText(firstName)
                lastNameView?.setText(lastName)
            }
        }
    }

    private fun initContentViews() {
        val textWatcher = object : TextWatcher by TextWatcherAdapter {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // hiding the floating action button if text is empty
                if(s.isEmpty()) doneFab?.hide()
            }

            override fun afterTextChanged(s: Editable) {
                if(isInputDataValid()) doneFab?.show()
            }
        }
        firstNameView?.addTextChangedListener(textWatcher)
        lastNameView?.addTextChangedListener(textWatcher)
        doneFab?.setOnClickListener {

        }
    }

    private fun isInputDataValid() = firstNameView?.text?.isNotEmpty() == true &&
            lastNameView?.text?.isNotEmpty() == true

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
