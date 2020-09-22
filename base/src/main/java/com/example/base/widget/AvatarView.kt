package com.example.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.Checkable
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.example.base.R
import com.example.base.helper.ApiLevelHelper
import com.example.base.model.Avatar
import com.example.base.widget.outlineprovider.RoundOutlineProvider

/**
 * A simple view that wraps an avatar
 */
class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle), Checkable {
    private var isChecked = false
    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.AvatarView, defStyle, 0)
        try {
            val avatarDrawableId = attributes.getResourceId(R.styleable.AvatarView_avatar, NOT_FOUND)
            if(avatarDrawableId != NOT_FOUND) {
                setAvatar(avatarDrawableId)
            }
        } finally {
            attributes.recycle()
        }
    }

    override fun setChecked(b: Boolean) {
        isChecked = b
        invalidate()
    }

    override fun isChecked() = isChecked

    override fun toggle() = setChecked(!isChecked)

    fun setAvatar(avatar: Avatar) {
        setAvatar(avatar.drawableId)
    }

    /**
     * Set the image for this avatar. Will be used to create a round version of this avatar.
     * @param resId The image's resource id
     */
    @SuppressLint("NewApi")
    fun setAvatar(@DrawableRes resId: Int) {
        if(ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
            clipToOutline = true
            setImageResource(resId)
        } else {
            setAvatarPreLollipop(resId)
        }
    }

    private fun setAvatarPreLollipop(@DrawableRes resId: Int) {
        val drawable = ResourcesCompat.getDrawable(resources, resId, context.theme)
                as BitmapDrawable
        val roundedDrawable = RoundedBitmapDrawableFactory.create(resources, drawable.bitmap)
            .apply {
                isCircular = true
            }
        setImageDrawable(roundedDrawable)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isChecked) {
            ContextCompat.getDrawable(context, R.drawable.selector_avatar)!!.apply {
                setBounds(0, 0, width, height)
                draw(canvas)
            }
        }
    }

    @SuppressLint("NewApi")
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (ApiLevelHelper.isLowerThan(Build.VERSION_CODES.LOLLIPOP)) {
            return
        }
        if (w > 0 && h > 0) {
            outlineProvider = RoundOutlineProvider(Math.min(w, h))
        }
    }

    companion object {
        private val NOT_FOUND = 0
    }

}