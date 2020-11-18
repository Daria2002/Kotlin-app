package com.example.w4e.start.adapter
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.w4e.start.fragment.CVAndExperienceTab
import com.example.w4e.start.fragment.InProgressTab
import com.example.w4e.start.fragment.PostTab
import com.example.w4e.start.fragment.ReviewTab
import com.example.w4e.start.helper.PostDatabaseHelper
import com.example.w4e.start.helper.UserDatabaseHelper


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
                PostTab.newInstance(userName)!!
            }
            1 -> {
                CVAndExperienceTab.newInstance(cvUrl)!!
            }
            2 -> {
                ReviewTab.newInstance(userName)!!
            }
            3 -> {
                InProgressTab.newInstance(userName)!!
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}