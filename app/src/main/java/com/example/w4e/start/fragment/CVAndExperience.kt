package com.example.w4e.start.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.example.w4e.start.R
import com.example.w4e.start.R.id
import com.example.w4e.start.R.id.*
import com.example.w4e.start.helper.FileDataPart
import com.example.w4e.start.helper.UserDatabaseHelper
import com.example.w4e.start.model.User
import kotlinx.android.synthetic.main.activity_category.*

class CVAndExperience: Fragment() {
    private lateinit var userName: String
    private lateinit var userCv: String
    private lateinit var cv_view: AppCompatTextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cv_view = view!!.findViewById<AppCompatTextView>(R.id.cv)
        cv_view.text = userCv
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cv_and_experience, container, false)
        /*
        var params = HashMap<String, FileDataPart>()
        params["documentFile"] = FileDataPart("cv_$userName", user.cv!!, "pdf")
         */
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, cv: ByteArray) = CVAndExperience().apply {
            userName = name
            userCv = cv.contentToString()
        }
    }
}