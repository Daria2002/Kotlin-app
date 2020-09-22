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
import android.widget.AdapterView
import android.widget.EditText
import android.widget.GridView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.base.R
import com.example.base.adapter.AvatarAdapter
import com.example.base.helper.*
import com.example.base.model.Avatar
import com.example.base.model.Worker
import com.example.base.widget.TextWatcherAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SignInFragment : Fragment() {
    private var firstNameView: EditText? = null
    private var lastNameView: EditText? = null
    private var worker: Worker? = null
    private var doneFab: FloatingActionButton? = null
    private val edit by lazy { arguments?.getBoolean(ARG_EDIT, false) ?: false }
    private var avatarGrid: GridView? = null
    private var selectedAvatar: Avatar? = null
    private var selectedAvatarView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if(savedInstanceState != null) {
            val avatarIndex = savedInstanceState.getInt(KEY_SELECTED_AVATAR_INDEX)
            if(avatarIndex != GridView.INVALID_POSITION) {
                selectedAvatar = Avatar.values()[avatarIndex]
            }
        }
        activity?.run {
            if(isLoggedIn()) {
                // navigateToCategoryActivity()
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
                        login.saveWorker(activity!!, this, {selectAvatar(it.avatar!!)})
                    }
                }
            }
        } else {
            navigateToCategoryActivity()
        }
    }

    private fun selectAvatar(avatar: Avatar) {
        selectedAvatar = avatar
        avatarGrid?.run {
            requestFocusFromTouch()
            setItemChecked(avatar.ordinal, true)
        }
        showFab()
    }

    private fun showFab() {
        if(isInputDataValid()) doneFab?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contentView = inflater.inflate(R.layout.fragment_sign_in, container, false)
        contentView.onLayoutChange {
            avatarGrid?.apply {
                adapter = AvatarAdapter(activity!!)
                onItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->
                    selectedAvatarView = view
                    selectedAvatar = Avatar.values()[position]
                    // showing the floating action button if input data is valid
                    showFab()
                }
                numColumns = calculateSpanCount()
                selectedAvatar?.run { selectAvatar(this) }
            }
        }
        return  contentView
    }

    /**
     * Calculates spans for avatars dynamically
     * @return The recommended amount of columns
     */
    private fun calculateSpanCount(): Int {
        val avatarSize = resources.getDimensionPixelSize(R.dimen.size_fab)
        val avatarPadding = resources.getDimensionPixelOffset(R.dimen.spacing_double)
        return (avatarGrid?.width ?: 0) / (avatarSize + avatarPadding)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firstNameView = view.findViewById<EditText>(R.id.first_name)
        lastNameView = view.findViewById<EditText>(R.id.last_name)
        doneFab = view.findViewById<FloatingActionButton>(R.id.done)
        avatarGrid = view.findViewById<GridView>(R.id.avatars)

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
                avatar?.run { selectAvatar(this) }
            }
        }
    }

    private fun isAvatarSelected() = selectedAvatarView != null || selectedAvatar != null

    private fun initContentViews() {
        val textWatcher = object : TextWatcher by TextWatcherAdapter {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // hiding the floating action button if text is empty
                if(s.isEmpty()) doneFab?.hide()
            }

            override fun afterTextChanged(s: Editable) {
                if(isAvatarSelected() && isInputDataValid()) doneFab?.show()
            }
        }
        firstNameView?.addTextChangedListener(textWatcher)
        lastNameView?.addTextChangedListener(textWatcher)
        doneFab?.setOnClickListener {
            if(it.id == R.id.done) {
                val first = firstNameView?.text?.toString()
                val last = lastNameView?.text?.toString()
                activity?.run {
                    val toSave = worker?.apply {
                        firstName = first
                        lastName = last
                        avatar = selectedAvatar
                    } ?: Worker(first, last, selectedAvatar)
                    login.saveWorker(this, toSave) {
                        Log.d(TAG, "Saving login info successful")
                    }
                }
            }
            removeDoneFab {
                performSignInWithTransition(selectedAvatarView
                    ?: avatarGrid?.getChildAt(selectedAvatar!!.ordinal))
            }
        }
    }

    private fun removeDoneFab(endAction: () -> Unit) {
        ViewCompat.animate(doneFab!!)
            .scaleX(0f)
            .scaleY(0f)
            .setInterpolator(FastOutSlowInInterpolator())
            .start()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun performSignInWithTransition(v: View? = null) {
        // TODO
    }

    private fun isInputDataValid() = firstNameView?.text?.isNotEmpty() == true &&
            lastNameView?.text?.isNotEmpty() == true && selectedAvatar != null

    companion object {
        private const val ARG_EDIT = "EDIT"
        private const val KEY_SELECTED_AVATAR_INDEX = "selectedAvatarIndex"

        fun newInstance(edit: Boolean = false): SignInFragment {
            return SignInFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_EDIT, edit)
                }
            }
        }
    }
}