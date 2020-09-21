package com.example.base.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat

class ActivityLaunchHelper {
    companion object {
        const val EXTRA_EDIT = "EDIT"
        private  const val URL_BASE = "https://work4experience.instantappsample.com"
        private const val URL_CATEGORIES = "$URL_BASE/categories"

        fun launchCategorySelection(activity: Activity, options: ActivityOptionsCompat? = null) {
            val starter = categorySelectionIntent(activity)
            if(options == null) {
                activity.startActivity(starter)
            } else {
                ActivityCompat.startActivity(activity, starter, options.toBundle())
            }
        }

        fun categorySelectionIntent(context: Context? = null) = baseIntent(URL_CATEGORIES, context)

        private fun baseIntent(url: String, context: Context? = null): Intent {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                .addCategory(Intent.CATEGORY_DEFAULT)
                .addCategory(Intent.CATEGORY_BROWSABLE)
            if (context != null) intent.`package` = context.packageName
            return intent
        }
    }
}