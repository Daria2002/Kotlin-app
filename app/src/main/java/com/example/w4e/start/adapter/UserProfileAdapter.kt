package com.example.w4e.start.adapter
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.w4e.start.fragment.CVAndExperience
import com.example.w4e.start.fragment.Review
import com.example.w4e.start.model.User

@Suppress("DEPRECATION")
internal class UserProfileAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    var user: User
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CVAndExperience()
            }
            1 -> {
                Review()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}