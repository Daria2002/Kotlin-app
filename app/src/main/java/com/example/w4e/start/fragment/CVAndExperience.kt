package com.example.w4e.start.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.w4e.start.R
import com.example.w4e.start.helper.FileDataPart
import com.example.w4e.start.model.User
import kotlinx.android.synthetic.main.activity_category.*

class CVAndExperience: Fragment() {
    private lateinit var userName: String
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
        fun newInstance(name: String) = CVAndExperience().apply {
            userName = name
        }
    }
}