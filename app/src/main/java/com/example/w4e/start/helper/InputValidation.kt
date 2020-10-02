package com.example.w4e.start.helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class InputValidation

    /**
     * Constructor
     * @param context
     */
    (private val context: Context) {
        /**
         * method to check InputEditText filled
         * @param textInputEditText
         * @param textInputLayout
         * @param message
         * @return
         */
        fun isInputEditTextFilled(textInputEditText: TextInputEditText,
                                  textInputLayout: TextInputLayout,
                                  message: String) : Boolean {
            val value = textInputEditText.text.toString().trim()
            if(value.isEmpty()) {
                textInputLayout.error = message
                hideKeyboardForm(textInputEditText)
                return false
            } else {
                textInputLayout.isErrorEnabled = false
            }
            return true
        }

        /**
         * method to check InputEditText has valid email
         * @param textInputEditText
         * @param textInputLayout
         * @param message
         * @return
         */
        fun isInputEditTextEmail(textInputEditText: TextInputEditText,
                                 textInputLayout: TextInputLayout,
                                 message: String) : Boolean {
            val value = textInputEditText.text.toString().trim()
            if(value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                textInputLayout.error = message
                hideKeyboardForm(textInputEditText)
                return false
            } else {
                textInputLayout.isErrorEnabled = false
            }
            return true
        }

        /**
         * method to check both InputEditText value
         * @param textInputEditText1
         * @param textInputEditText2
         * @param message
         * @return
         */

        /**
         * method to Hide keyboard
         * @param view
         */
        private fun hideKeyboardForm(view: View) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
}