package com.example.w4e.start.adapter
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.w4e.start.R
import com.example.w4e.start.fragment.CVAndExperience
import com.example.w4e.start.fragment.Review


@Suppress("DEPRECATION")
internal class UserProfileAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    var cvUrl: String,
    var userName: String
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CVAndExperience.newInstance(cvUrl)!!
            }
            1 -> {
                Review.newInstance(userName)!!
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}