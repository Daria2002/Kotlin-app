package com.example.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.base.R
import com.example.base.model.Avatar
import com.example.base.widget.AvatarView

/**
 * Adapter to display icons
 */
class AvatarAdapter(context: Context) : BaseAdapter() {
    private val layoutInflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): AvatarView {
        return ((convertView ?:
                layoutInflater.inflate(R.layout.item_avatar, parent, false)) as AvatarView)
            .also { setAvatar(it, avatars[position]) }
    }
    private fun setAvatar(view: AvatarView, avatar: Avatar) {
        with(view) {
            setAvatar(avatar.drawableId)
            contentDescription = avatar.nameForAccessibility
        }
    }

    override fun getCount() = avatars.size
    override fun getItem(position: Int) = avatars[position]
    override fun getItemId(position: Int) = position.toLong()
    companion object {
        private val avatars = Avatar.values()
    }
}