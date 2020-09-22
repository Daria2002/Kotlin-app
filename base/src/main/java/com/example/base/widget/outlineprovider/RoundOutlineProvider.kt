package com.example.base.widget.outlineprovider

import android.annotation.TargetApi
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider

/**
 * Creates round outlines for views.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class RoundOutlineProvider(private val size: Int) : ViewOutlineProvider() {

    init {
        if (0 > size) throw IllegalArgumentException("size needs to be > 0. Actually was $size")
    }

    override fun getOutline(view: View, outline: Outline) = outline.setOval(0, 0, size, size)

}
