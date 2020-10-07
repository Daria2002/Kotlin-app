package com.example.w4e.start.fragment

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.view.View
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.example.w4e.start.R
import com.example.w4e.start.adapter.CategoryAdapter
import com.example.w4e.start.helper.ActivityLaunchHelper
import com.example.w4e.start.helper.TransitionHelper
import com.example.w4e.start.model.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategorySelectionFragment : Fragment() {
    private val adapter: CategoryAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        activity?.run {
            CategoryAdapter(this,
                AdapterView.OnItemClickListener { _, v, position, _ ->
                    adapter?.getItem(position)?.let {
                        startCategoryWithTransition(this,
                            v.findViewById(R.id.category_title), it)
                    }
                })
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startCategoryWithTransition(activity: Activity, toolbar: View,
                                            category: Category) {
        val animationBundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
            *TransitionHelper.createSafeTransitionParticipants(activity,
                false,
                Pair(toolbar, "ToolbarTransition")))
            .toBundle()
        val startIntent = ActivityLaunchHelper.categoryIntent(category, activity)
        ActivityCompat.startActivityForResult(activity,
                startIntent,
                REQUEST_CATEGORY,
                animationBundle)
    }

    companion object {
        internal const val REQUEST_CATEGORY = 0x2300
    }
}
